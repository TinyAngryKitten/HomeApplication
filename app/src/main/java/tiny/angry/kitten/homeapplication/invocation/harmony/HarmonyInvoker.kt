package tiny.angry.kitten.homeapplication.invocation.harmony
import android.util.Log
import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.core.buffer.Buffer
import io.vertx.mqtt.MqttClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import tiny.angry.kitten.topics.harmony.*

class HarmonyInvoker : KoinComponent {
    private val client : MqttClient by inject()
    private val topicDefinition = HarmonyHubTopic

    fun invokeAction(device: HarmonyHubDevices, command: HarmonyHubActions) {
        Log.d("MQTT","Invoking harmony command")
        client.publish(
            topicDefinition.createTopicWith(device = device.toString()),
            Buffer.buffer(topicDefinition.createPayload(command)),
            MqttQoS.EXACTLY_ONCE,
            false,
            false
        )
    }
}