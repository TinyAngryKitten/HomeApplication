package tiny.angry.kitten.homeapplication.events

import io.vertx.mqtt.messages.MqttPublishMessage

interface MqttEventHandler {
    /**
     * The action which is invoked on receiving a message on subscribed topics
     */
    fun handle(message : MqttPublishMessage)

    //TODO: this assumes that one does not subscribe to parts of another topic like pc/power and pc/power/watts, pc/power will match for /watts too
    fun canHandle(message: MqttPublishMessage): Boolean {
        val msgTopicParts = message.topicName().split("/")

        topics.forEach{ topic ->
            if(topicMatches(msgTopicParts,topic.split("/"))) return true
        }

        return false
    }

    fun topicMatches(msgTopic : List<String>, matchingTopic : List<String>) : Boolean {
        if(msgTopic.isEmpty() || matchingTopic.isEmpty()) return true
        if(matchingTopic.first() != "+") {
            if(matchingTopic.first() != msgTopic.first()) return false
        }
        return topicMatches(msgTopic.drop(1),matchingTopic.drop(1))
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