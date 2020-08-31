package tiny.angry.kitten.homeapplication.initializers

import android.content.Context
import androidx.startup.Initializer
import kotlinx.coroutines.Job
import tiny.angry.kitten.homeapplication.events.MqttThread

class MqttInitializer : Initializer<MqttThread> {
    override fun create(context: Context) = MqttThread().apply { connect() }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf(KoinInitializer::class.java)
}