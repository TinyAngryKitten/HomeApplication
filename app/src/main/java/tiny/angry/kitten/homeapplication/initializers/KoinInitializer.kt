package tiny.angry.kitten.homeapplication.initializers

import android.content.Context
import androidx.startup.Initializer
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import tiny.angry.kitten.homeapplication.modules.mqttModule

class KoinInitializer : Initializer<KoinApplication> {
    override fun create(context: Context) =
        startKoin {
            androidContext(context)

            modules(
                mqttModule
            )
        }

    override fun dependencies() = mutableListOf<Class<out Initializer<*>>>()

}