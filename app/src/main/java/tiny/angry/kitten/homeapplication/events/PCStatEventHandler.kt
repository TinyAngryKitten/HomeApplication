package tiny.angry.kitten.homeapplication.events

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.mqtt.MqttClient
import io.vertx.mqtt.messages.MqttPublishMessage
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import tiny.angry.kitten.homeapplication.data.PCStats
import java.util.Collections.synchronizedList

class PCStatEventHandler : KoinComponent, MqttEventHandler{
    private val pcname : String by inject(named("pcname"))
    private val jsonMapper : ObjectMapper by inject()

    override val qos = MqttQoS.AT_MOST_ONCE.value()

    override val topics = listOf(
        "computer/$pcname/cpu",
        "computer/$pcname/gpu"
    )

    override fun handle(message : MqttPublishMessage) {
        val isCpu = message.topicName().split("/").last() == "cpu"
        if(isCpu) {
            val stats : PCStats.CPUStats = jsonMapper.readValue(message.payload().toString())
        } else {
            val stats : PCStats.GPUStats = jsonMapper.readValue(message.payload().toString())
        }
    }
}