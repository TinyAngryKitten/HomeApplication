package tiny.angry.kitten.homeapplication.events

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.mqtt.MqttClient
import io.vertx.mqtt.messages.MqttPublishMessage
import kotlinx.coroutines.CoroutineScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import tiny.angry.kitten.homeapplication.data.PCStats
import tiny.angry.kitten.homeapplication.viewmodels.PCStatsViewModel
import java.util.Collections.synchronizedList

class PCStatEventHandler : KoinComponent, MqttEventHandler{
    private val pcname : String by inject(named("pcname"))
    private val jsonMapper : ObjectMapper by inject()
    private val viewModel : PCStatsViewModel by inject()

    override val qos = MqttQoS.AT_MOST_ONCE.value()

    override val topics = listOf(
        "computer/$pcname/cpu/load",
        "computer/$pcname/cpu/temp",
        "computer/$pcname/cpu/fan",
        "computer/$pcname/cpu/usedRam",
        "computer/$pcname/cpu/totalRam",

        "computer/$pcname/gpu/load",
        "computer/$pcname/gpu/temp",
        "computer/$pcname/gpu/fan",
        "computer/$pcname/gpu/usedVRam",
        "computer/$pcname/gpu/totalVRam"
    )

    override fun handle(message : MqttPublishMessage) {
        val system = message.topicName().split("/")[2]
        val metric = message.topicName().split("/")[3]
        val data = message.payload().toString().toDouble().toInt()

        Handler(Looper.getMainLooper()).post {

            when (system) {
                "cpu" -> when (metric) {
                    "load" -> viewModel.cpuLoad.value = data
                    "temp" -> viewModel.cpuTemp.value = data
                    "fan" -> viewModel.cpuFan.value = data
                    "usedRam" -> viewModel.usedRam.value = data
                    "totalRam" -> viewModel.totalRam.value = data
                }

                "gpu" -> when (metric) {
                    "load" -> viewModel.gpuLoad.value = data
                    "temp" -> viewModel.gpuTemp.value = data
                    "fan" -> viewModel.gpuFan.value = data
                    "usedRam" -> viewModel.usedVRam.value = data
                    "totalRam" -> viewModel.totalVRam.value = data
                }
            }
        }
    }
}