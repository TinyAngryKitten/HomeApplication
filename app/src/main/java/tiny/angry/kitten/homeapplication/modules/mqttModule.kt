package tiny.angry.kitten.homeapplication.modules

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat.requestPermissions
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.mqtt.MqttClient
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tiny.angry.kitten.homeapplication.events.*
import tiny.angry.kitten.homeapplication.invocation.lights.LightController
import tiny.angry.kitten.homeapplication.invocation.media.MediaFunction
import tiny.angry.kitten.homeapplication.invocation.media.MediaFunctionInvocation
import tiny.angry.kitten.homeapplication.viewmodels.AirqualityViewModel
import tiny.angry.kitten.homeapplication.viewmodels.LightStateViewModel
import tiny.angry.kitten.homeapplication.viewmodels.PCStatsViewModel
import tiny.angry.kitten.homeapplication.viewmodels.ScreenStateViewModel
import java.util.Collections.synchronizedList

val mqttModule = module {
    single { Vertx.vertx(get()) }
    single { VertxOptions() }

    single { MqttClient.create(get()) }

    single(named("messageHandlers")) {
        synchronizedList<MqttEventHandler>(listOf(
            PCStatEventHandler(),
            AppScreenEventHandler(),
            LeagueEventHandler(),
            LightStateEventHandler()
        ))
    }

    single(named("pcname")) {"gamingpc"}

    single{
        ObjectMapper().registerModule(KotlinModule())
    }

    single(named("host")) {"192.168.50.3"}
    single(named("port")) {1883}

    single { PCStatsViewModel() }
    single { LightController() }

    single(named("invokeMediaFunction")) {MediaFunctionInvocation() as (MediaFunction) -> Unit}

    single { ScreenStateViewModel(androidContext()) }
    single { LightStateViewModel() }
    single { AirqualityViewModel() }

    single(named("placement")) { "livingroom" }

    single(named("connectionState")) { MutableStateFlow(false)}

    single { FirebaseCrashlytics.getInstance() }
}