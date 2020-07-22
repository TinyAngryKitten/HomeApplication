package tiny.angry.kitten.homeapplication.events

import android.util.Log
import io.vertx.mqtt.MqttClient
import io.vertx.mqtt.messages.MqttPublishMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class MqttThread : KoinComponent{
    val client : MqttClient by inject()
    val host : String by inject(named("host"))
    val port : Int by inject(named("port"))
    val handlers: List<MqttEventHandler> by inject(named("messageHandlers"))
    var stayConnected = true


    fun connect() {
        GlobalScope.launch {
            while(stayConnected) {

                if (!client.isConnected) {
                    client.connect(port, host) { connectionMessage ->
                        Log.i("MQTT", "Connected to broker")

                        client.publishHandler(::handleMessages)
                        subscribeToHandlerTopics()
                    }
                }

                delay(10000)
            }
        }
    }

    fun handleMessages(message: MqttPublishMessage) =
            handlers.forEach {
                if(it.canHandle(message)) it.handle(message)
            }

    fun subscribeToHandlerTopics() {
        Log.i("MQTT", "subscribing to topics")
        handlers.forEach {handler ->
            handler.topics.forEach { Log.i("MQTT", it) }
            client.subscribe(
                handler.topics
                    .map { it to handler.qos }
                    .toMap()
            )
        }
    }
}