package tiny.angry.kitten.homeapplication.modules

import android.os.Handler
import android.os.Looper
import arrow.core.Option
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.netty.handler.codec.mqtt.MqttPublishMessage
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.mqtt.MqttClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tiny.angry.kitten.homeapplication.events.AppScreenEventHandler
import tiny.angry.kitten.homeapplication.events.LeagueEventHandler
import tiny.angry.kitten.homeapplication.events.MqttEventHandler
import tiny.angry.kitten.homeapplication.events.PCStatEventHandler
import tiny.angry.kitten.homeapplication.invocation.media.MediaFunction
import tiny.angry.kitten.homeapplication.invocation.media.MediaFunctionInvocation
import tiny.angry.kitten.homeapplication.viewmodels.PCStatsViewModel
import java.util.*
import java.util.Collections.synchronizedList

val mqttModule = module {
    single { Vertx.vertx(get()) }
    single { VertxOptions() }

    single{ MqttClient.create(get()) }

    single(named("messageHandlers")) {
        synchronizedList<MqttEventHandler>(listOf(
            PCStatEventHandler(),
            AppScreenEventHandler(),
            LeagueEventHandler()
        ))
    }

    single(named("pcname")) {"gamingpc"}

    single{
        ObjectMapper().registerModule(KotlinModule())
    }

    single(named("host")) {"10.0.0.96"}
    single(named("port")) {1883}

    single { PCStatsViewModel() }

    single(named("setScreenOn")) {
        ScreenSingleton
    }

    single(named("invokeMediaFunction")) {MediaFunctionInvocation() as (MediaFunction) -> Unit}
}

object ScreenSingleton {
    private var setScreenOnMethod : (Boolean) -> Unit = {}
    fun changeScreenOnMethod(fn : (Boolean) -> Unit) {
        setScreenOnMethod = fn
    }

    fun setScrenOn(on : Boolean) = Handler(Looper.getMainLooper()).post {
        setScreenOnMethod(on)
    }
}