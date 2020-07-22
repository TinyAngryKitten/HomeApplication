package tiny.angry.kitten.homeapplication

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tiny.angry.kitten.homeapplication.events.MqttThread
import tiny.angry.kitten.homeapplication.modules.mqttModule

class MainApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@MainApplication)
            // declare modules
            modules(mqttModule)
        }
        MqttThread().connect()
    }
}