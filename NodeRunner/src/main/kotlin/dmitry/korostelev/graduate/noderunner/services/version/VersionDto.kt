package dmitry.korostelev.graduate.noderunner.services.version

import org.springframework.boot.info.BuildProperties

data class VersionDto(
    val service: String,
    val version: String
) {
    companion object {
        fun of(buildProperties: BuildProperties) = VersionDto(
            buildProperties.name,
            buildProperties.version
        )
    }
}

