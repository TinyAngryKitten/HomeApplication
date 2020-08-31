package tiny.angry.kitten.homeapplication.events

import android.util.Log
import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.mqtt.messages.MqttPublishMessage
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import tiny.angry.kitten.homeapplication.data.LightState
import tiny.angry.kitten.homeapplication.viewmodels.LightStateViewModel

class LightStateEventHandler : MqttEventHandler, KoinComponent {
    val viewmodel : LightStateViewModel by inject()

    override fun handle(message: MqttPublishMessage) {
        val topicParts = message.topicName().split("/")
        val group = topicParts[2]
        val parameter = topicParts[4]

        when(parameter) {
            "brightness" -> updateBrightness(group,message.payload().toString().toInt())
            "color" -> Unit
            "state" -> updateState(group,message.payload().toString()=="on")
            else -> Log.e("MQTT","received unsupprted light state parameter: $parameter")
        }
    }

    fun updateBrightness(group : String, value : Int) {
        MainScope().launch {
            val currentState = viewmodel.lightState.value?.get(group) ?: LightState()
            viewmodel.lightState.value =
                viewmodel.lightState.value?.plus(Pair(group, currentState.copy(brightness = value)))
        }
    }

    fun updateState(group : String, state : Boolean) {
        MainScope().launch {
            val currentState = viewmodel.lightState.value?.get(group) ?: LightState()
            viewmodel.lightState.value =
                viewmodel.lightState.value?.plus(Pair(group, currentState.copy(state = state)))
        }
    }

    override val topics: List<String>
        get() = listOf("light/group/+/update/+")
    override val qos: Int
        get() = MqttQoS.AT_MOST_ONCE.value()
}