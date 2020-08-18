package tiny.angry.kitten.homeapplication.invocation.lights

import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.core.buffer.Buffer
import io.vertx.mqtt.MqttClient
import org.koin.core.KoinComponent
import org.koin.core.inject

class LightController : KoinComponent {
    val client : MqttClient by inject()
    val topic = "light/group/"
    val qos = MqttQoS.AT_LEAST_ONCE

    fun adjustBrightnessOfGroup(group: String, brightness : Int) =
        client.publish(
            "$topic/$group/brightness",
            Buffer.buffer(brightness.toString()),
            qos,
            false,
            false
        )


}