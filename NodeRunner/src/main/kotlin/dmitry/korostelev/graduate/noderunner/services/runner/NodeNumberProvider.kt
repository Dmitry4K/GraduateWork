package dmitry.korostelev.graduate.noderunner.services.runner

interface NodeNumberProvider {
    fun getNodeNumberForService(name: String): Int
}