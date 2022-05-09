package dmitry.korostelev.graduate.noderunner.services.runner

interface RunnerService {
    fun getContainers(): List<String>
    fun getImages(): List<String>
    fun stopAll()
    fun startService(name: String): Int
    fun stopService(port: Int)
}