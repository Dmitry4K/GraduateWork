package dmitry.korostelev.graduate.noderunner.rest

import dmitry.korostelev.graduate.noderunner.services.runner.RunnerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RunnerController(
    private val runnerService: RunnerService
) {
    @GetMapping("/containers")
    fun containers(): List<String> = runnerService.getContainers()

    @GetMapping("/images")
    fun images(): List<String> = runnerService.getImages()

    @GetMapping("/stop/all")
    fun stopAll() = runnerService.stopAll()

    @GetMapping("/start/service")
    fun startService(name: String) = runnerService.startService(name)

    @GetMapping("/stop/service")
    fun stopService(port: Int) = runnerService.stopService(port)
}