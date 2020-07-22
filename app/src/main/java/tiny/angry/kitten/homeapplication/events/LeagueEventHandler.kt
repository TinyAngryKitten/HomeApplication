package tiny.angry.kitten.homeapplication.events

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.mqtt.messages.MqttPublishMessage
import org.koin.core.KoinComponent
import org.koin.core.inject


class LeagueEventHandler : MqttEventHandler, KoinComponent {
    val context : Context by inject()
    val region = "euw"

    override fun handle(message: MqttPublishMessage) {
        val username = message.topicName().split("/")[1]

        val url = "https://porofessor.gg/live/$region/$username"
        Log.i("MQTT", "LEAGUE MESSAGE RECEIVED")
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }

    override val topics: List<String>
        get() = listOf("league/+")
    override val qos: Int
        get() = MqttQoS.AT_MOST_ONCE.value()
}