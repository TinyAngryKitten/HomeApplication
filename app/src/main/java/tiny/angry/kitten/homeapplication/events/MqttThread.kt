package tiny.angry.kitten.homeapplication.events

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.vertx.mqtt.MqttClient
import io.vertx.mqtt.messages.MqttPublishMessage
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

class MqttThread : Service(), KoinComponent{
    val client : MqttClient by inject()
    val host : String by inject(named("host"))
    val port : Int by inject(named("port"))
    val handlers: List<MqttEventHandler> by inject(named("messageHandlers"))
    val crashlytics : FirebaseCrashlytics by inject()

    var stayConnected = true
    var mqttJob : Job = Job().apply { cancel() }

    fun connect() {
        client.exceptionHandler(::handleException)
        
        mqttJob = MainScope().launch {
            withContext(Dispatchers.IO) {
                while (stayConnected) {

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
    }

    fun handleException(exception : Throwable) {
        crashlytics.recordException(exception)
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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        connect()
        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? = null
}