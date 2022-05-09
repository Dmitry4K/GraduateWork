package dmitry.korostelev.graduate.noderunner.services.version.impl

import dmitry.korostelev.graduate.noderunner.services.version.VersionDto
import dmitry.korostelev.graduate.noderunner.services.version.VersionService
import org.springframework.boot.info.BuildProperties
import org.springframework.stereotype.Service

@Service
class VersionServiceImpl(
    var buildProperties: BuildProperties
) : VersionService {
    override fun getVersion(): VersionDto = VersionDto.of(buildProperties)
}