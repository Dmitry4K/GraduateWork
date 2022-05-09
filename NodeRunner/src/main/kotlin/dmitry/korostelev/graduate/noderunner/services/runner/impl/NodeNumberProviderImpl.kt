package dmitry.korostelev.graduate.noderunner.services.runner.impl

import com.github.dockerjava.api.DockerClient
import dmitry.korostelev.graduate.noderunner.services.runner.NodeNumberProvider
import dmitry.korostelev.graduate.noderunner.services.runner.ServiceNodeName
import org.springframework.stereotype.Component

@Component
class NodeNumberProviderImpl(val dockerClient: DockerClient): NodeNumberProvider {
    private val mutex = Object()
    override fun getNodeNumberForService(name: String): Int {
        synchronized(mutex) {
            val nodesNumbers = dockerClient.listContainersCmd()
                .withShowAll(true)
                .exec()
                .map { ServiceNodeName.getNodeNumber(it.names[0]) }
            val maxNodeNumber = nodesNumbers.maxOrNull() ?: 0
            val minNodeNumber = nodesNumbers.minOrNull() ?: 0
            return try {
                (minNodeNumber..maxNodeNumber).first { it !in nodesNumbers }
            } catch (e: Exception) {
                (maxNodeNumber + 1)
            }
        }
    }
}