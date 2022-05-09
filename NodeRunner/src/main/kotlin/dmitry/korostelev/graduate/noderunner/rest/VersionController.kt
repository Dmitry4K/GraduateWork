package dmitry.korostelev.graduate.noderunner.rest

import dmitry.korostelev.graduate.noderunner.services.version.VersionDto
import dmitry.korostelev.graduate.noderunner.services.version.VersionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/version")
class VersionController(
    val versionService: VersionService
) {
    @GetMapping
    fun version(): VersionDto = versionService.getVersion()
}