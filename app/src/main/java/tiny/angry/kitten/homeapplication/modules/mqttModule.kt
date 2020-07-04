package tiny.angry.kitten.homeapplication.modules

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.netty.handler.codec.mqtt.MqttPublishMessage
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.mqtt.MqttClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.Collections.synchronizedList

val mqttModule = module {
    single { Vertx.vertx(get()) }
    single { VertxOptions() }

    single{ MqttClient.create(get()) }

    single(named("messageHandlers")) {
        synchronizedList<(MqttPublishMessage)->Unit>(listOf())
    }

    single(named("pcname")) {"gamingpc"}

    single{
        ObjectMapper().registerModule(KotlinModule())
    }
}