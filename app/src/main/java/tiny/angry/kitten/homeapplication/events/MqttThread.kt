package tiny.angry.kitten.homeapplication.events

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.netty.handler.codec.mqtt.MqttQoS
import io.vertx.core.buffer.Buffer
import io.vertx.mqtt.MqttClient
import io.vertx.mqtt.messages.MqttPublishMessage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class MqttThread : Service(), KoinComponent{
    val client : MqttClient by inject()
    val host : String by inject(named("host"))
    val port : Int by inject(named("port"))
    val handlers: List<MqttEventHandler> by inject(named("messageHandlers"))
    val crashlytics : FirebaseCrashlytics by inject()
    val stateFlow : MutableStateFlow<Boolean> by inject(named("connectionState"))

    var stayConnected = true
    var mqttJob : Job = Job().apply { cancel() }

    fun connect() {
        Log.d("MQTT", "Connecting to MQTT")
        client.exceptionHandler(::handleException)

        mqttJob = MainScope().launch {
            withContext(Dispatchers.IO) {
                while (stayConnected) {

                    if (!client.isConnected) {
                        stateFlow.value = false

                        client.connect(port, host) { connectionMessage ->
                            Log.i("MQTT", "Connected to broker")
                            stateFlow.value = true

                            client.publishHandler(::handleMessages)
                            subscribeToHandlerTopics()
                            fetchCurrentLightStates()
                        }
                    }

                    delay(10000)
                }
            }
        }
    }

    fun handleException(exception : Throwable) {
        crashlytics.recordException(exception)
    }

    fun handleMessages(message: MqttPublishMessage) =
            handlers.forEach {
                if(it.canHandle(message)) it.handle(message)
            }

    fun fetchCurrentLightStates() {
        client.publish(
            "light/group/+/update",
            Buffer.buffer(""),
            MqttQoS.AT_MOST_ONCE,
            false,
            false
        )
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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        connect()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}