package dmitry.korostelev.graduate.noderunner.services.runner.impl

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.exception.NotModifiedException
import com.github.dockerjava.api.model.*
import dmitry.korostelev.graduate.noderunner.services.runner.NodeNumberProvider
import dmitry.korostelev.graduate.noderunner.services.runner.PortProvider
import dmitry.korostelev.graduate.noderunner.services.runner.RunnerService
import dmitry.korostelev.graduate.noderunner.services.runner.ServiceNodeName
import org.springframework.stereotype.Service

@Service
class RunnerServiceImpl(
    val dockerClient: DockerClient,
    val portProvider: PortProvider,
    val nodeNumberProvider: NodeNumberProvider
) : RunnerService {

    override fun getContainers(): List<String> = getAllContainers()
        .map { it.image }
        .toList()

    override fun getImages(): List<String> = dockerClient
        .listImagesCmd()
        .withShowAll(true)
        .exec()
        .map { it.getNameWithVersion() }
        .toList()

    override fun stopAll() {
        val threads = getAllContainers().map { Thread {
            stopContainer(it.id)
            removeContainer(it.id)
        } }
        threads.forEach(Thread::start)
        threads.forEach(Thread::join)
    }

    override fun startService(name: String): Int = portProvider.getPort().let {
        startContainer(createContainerByImageWithPortsAndGetId(
            findImageByName(name), nodeNumberProvider.getNodeNumberForService(name), it
        ))
        it
    }

    override fun stopService(port: Int) {
        getAllContainers()
            .first { container -> port in container.ports.map { it.publicPort } }
            .id
            .let {
                stopContainer(it)
                removeContainer(it)
            }
    }

    private fun startContainer(containerId: String) {
        dockerClient
            .startContainerCmd(containerId)
            .exec()
    }

    private fun removeContainer(containerId: String) {
        dockerClient
            .removeContainerCmd(containerId)
            .exec()
    }

    private fun stopContainer(containerId: String) {
        try { dockerClient.stopContainerCmd(containerId).exec() } catch(_: NotModifiedException) {}
    }

    private fun createContainerByImageWithPortsAndGetId(
        image: Image, nodeNumber: Int, port: Int
    ) = ServiceNodeName(image.getName(), nodeNumber).toString().let {
        dockerClient
            .createContainerCmd(image.id)
            .withName(it)
            .withHostConfig(HostConfig().withPortBindings(PortBinding.parse("0.0.0.0:$port:8080")))
            .withExposedPorts(ExposedPort(8080))
            .exec()
        it
    }

    private fun findImageByName(name: String) = dockerClient
        .listImagesCmd()
        .exec()
        .first { it.getName() == name }

    private fun getAllContainers() = dockerClient
        .listContainersCmd()
        .withShowAll(true)
        .exec()
}

fun Image.getNameWithVersion(): String = this.repoTags[0]

fun Image.getName(): String = this.getNameWithVersion().split(':').first()

fun Image.getVersion(): String = this.getNameWithVersion().split(':').last()
