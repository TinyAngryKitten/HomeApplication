package tiny.angry.kitten.homeapplication.events

import android.util.Log
import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.mqtt.messages.MqttPublishMessage
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import tiny.angry.kitten.homeapplication.util.ScreenUtil

class AppScreenEventHandler : KoinComponent, MqttEventHandler{
    private val setScrenOn : (Boolean)->Unit by inject(named("setScreenOn"))
    private val placement : String by inject(named("placement"))

    override val topics: List<String>
        get() = listOf(
            "homeapp/screen",
            "home/$placement/movement"
        )
    override val qos: Int
        get() = MqttQoS.AT_MOST_ONCE.value()


    override fun handle(message: MqttPublishMessage) {
        val topic = message.topicName()

        when {
            topic.startsWith("homeapp") -> setScrenOn(
                message
                    .payload()
                    .toString()
                    .equals("on",true)
            )

            topic.startsWith("home/$placement/movement") ->
                if(message.payload().toString().toInt() > 0) ScreenUtil.wakeScreen()
                else ScreenUtil.releaseScreen()
        }
    }
}