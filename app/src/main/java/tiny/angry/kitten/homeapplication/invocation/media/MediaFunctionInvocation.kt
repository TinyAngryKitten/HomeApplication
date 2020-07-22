package tiny.angry.kitten.homeapplication.invocation.media

import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.core.buffer.Buffer
import io.vertx.mqtt.MqttClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class MediaFunctionInvocation : (MediaFunction) -> Unit, KoinComponent {
    val client : MqttClient by inject()
    val pcname : String by inject(named("pcname"))
    val topic = "computer/$pcname/media"
    val qos = MqttQoS.AT_MOST_ONCE

    override fun invoke(function: MediaFunction) {
        client.publish(
            topic,
            Buffer.buffer(function.toString()),
            qos,
            false,
            false
        )
    }
}