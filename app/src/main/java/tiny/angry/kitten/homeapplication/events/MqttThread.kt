package tiny.angry.kitten.homeapplication.events

import android.util.Log
import io.vertx.mqtt.MqttClient
import io.vertx.mqtt.messages.MqttPublishMessage
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class MqttThread : KoinComponent{
    val client : MqttClient by inject()
    val host : String by inject(named("host"))
    val port : Int by inject(named("ip"))
    val handlers: List<MqttEventHandler> by inject(named("messageHandlers"))


    fun connect() {
        client.connect(port,host) { connectionMessage ->
            Log.i("MQTT", "Connected to broker")

            client.publishHandler(::handleMessages)
            subscribeToHandlerTopics()
        }
    }

    fun handleMessages(message: MqttPublishMessage) =
            handlers.forEach {
                if(it.canHandle(message)) it.handle(message)
            }

    fun subscribeToHandlerTopics() {
        handlers.forEach {handler ->
            client.subscribe(
                handler.topics
                    .map { it to handler.qos }
                    .toMap()
            )
        }
    }
}