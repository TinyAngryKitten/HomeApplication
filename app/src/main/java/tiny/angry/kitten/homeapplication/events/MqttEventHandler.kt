package tiny.angry.kitten.homeapplication.events

import io.vertx.mqtt.messages.MqttPublishMessage

interface MqttEventHandler {
    /**
     * The action which is invoked on receiving a message on subscribed topics
     */
    fun handle(message : MqttPublishMessage)

    fun canHandle(message: MqttPublishMessage): Boolean {
        topics.forEach{if(it == message.topicName()) return true}
        return false
    }

    /**
     * List of topics used by this handler
     */
    val topics : List<String>

    /**
     * QoS level
     */
    val qos : Int
}