package tiny.angry.kitten.homeapplication.events

import android.util.Log
import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.mqtt.messages.MqttPublishMessage
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import tiny.angry.kitten.homeapplication.modules.ScreenSingleton

class AppScreenEventHandler : KoinComponent, MqttEventHandler{
    private val screenManager : ScreenSingleton by inject(named("setScreenOn"))

    override val topics: List<String>
        get() = listOf(
            "homeapp/screen"
        )
    override val qos: Int
        get() = MqttQoS.AT_MOST_ONCE.value()


    override fun handle(message: MqttPublishMessage) {
        screenManager.setScrenOn(
            message
                .payload()
                .toString()
                .equals("on",true)
        )
    }
}