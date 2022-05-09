package dmitry.korostelev.graduate.noderunner.configs

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DockerClientConfig {
    @Bean
    fun dockerClient(): DockerClient = DefaultDockerClientConfig
        .createDefaultConfigBuilder()
        .build()
        .let { dockerConfig ->
            DockerClientImpl.getInstance(
                dockerConfig, ApacheDockerHttpClient.Builder()
                    .dockerHost(dockerConfig.dockerHost)
                    .sslConfig(dockerConfig.sslConfig)
                    .build()
            )
        }
}