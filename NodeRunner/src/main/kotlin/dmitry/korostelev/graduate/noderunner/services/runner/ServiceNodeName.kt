package dmitry.korostelev.graduate.noderunner.services.runner

data class ServiceNodeName(
    var serviceName: String,
    var nodeNumber: Int
) {
    override fun toString(): String {
        return "$serviceName-node-$nodeNumber";
    }
    companion object {
        fun getNodeNumber(serviceNodeName: String) = serviceNodeName.split('-').last().toInt()
    }
}