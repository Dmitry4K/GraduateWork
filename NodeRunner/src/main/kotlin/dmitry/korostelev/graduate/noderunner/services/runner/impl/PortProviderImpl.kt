package dmitry.korostelev.graduate.noderunner.services.runner.impl

import com.github.dockerjava.api.DockerClient
import dmitry.korostelev.graduate.noderunner.services.runner.PortProvider
import org.springframework.stereotype.Component
import java.io.IOException

import java.net.ServerSocket




private val portRange = (49010 to 49150)

@Component
class PortProviderImpl(val dockerClient: DockerClient): PortProvider{
    private val mutex = Object()
    override fun getPort(): Int = synchronized(mutex) {
        dockerClient.listContainersCmd()
            .exec()
            .map { it.ports[0].publicPort }
            .let { usedPorts ->
                (portRange.first..portRange.second)
                    .first { it !in usedPorts && isAvailable(it) }
            }
    }

    private fun isAvailable(portNr: Int): Boolean {
        var portFree: Boolean
        try {
            ServerSocket(portNr).use { portFree = true }
        } catch (e: IOException) {
            portFree = false
        }
        return portFree
    }
}