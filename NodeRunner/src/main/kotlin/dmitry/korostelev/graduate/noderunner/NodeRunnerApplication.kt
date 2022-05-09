package dmitry.korostelev.graduate.noderunner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NodeRunnerApplication

fun main(args: Array<String>) {
    runApplication<NodeRunnerApplication>(*args)
}
