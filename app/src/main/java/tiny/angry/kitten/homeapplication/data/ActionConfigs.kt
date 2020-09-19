package tiny.angry.kitten.homeapplication.data

import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.core.buffer.Buffer
import io.vertx.mqtt.MqttClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.topics.harmony.HarmonyHubActions
import tiny.angry.kitten.topics.harmony.HarmonyHubDevices
import tiny.angry.kitten.topics.harmony.HarmonyHubTopic

object ActionConfigs : KoinComponent {
    val client : MqttClient by inject()

    fun fromResourceName(name : String) = when(name) {
        "leaving" -> ActionConfig(icon = R.mipmap.exit_icon_foreground, name = "Im leaving") {}
        "home" -> ActionConfig(icon = R.mipmap.house_icon_foreground, name = "Im home") {}
        "fans" -> ActionConfig(icon = R.mipmap.fan_icon_foreground,name = "Toggle fans") {
            publishMessage(
                HarmonyHubTopic.createTopicWith(device = HarmonyHubDevices.FANS.toString()),
                HarmonyHubTopic.createPayload(HarmonyHubActions.PowerToggle)
            )
        }
        "movie" -> ActionConfig(icon = R.mipmap.movie_icon_foreground ,name = "Movie night") {}
        else -> ActionConfig()
    }

    fun publishMessage(topic : String, message: String) =
        client.publish(
            topic,
            Buffer.buffer(message),
            MqttQoS.EXACTLY_ONCE,
            false,
            false
        )
}