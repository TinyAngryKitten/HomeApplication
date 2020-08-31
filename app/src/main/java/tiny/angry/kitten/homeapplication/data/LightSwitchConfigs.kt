package tiny.angry.kitten.homeapplication.data

import tiny.angry.kitten.homeapplication.data.LightSwitchConfig

object LightSwitchConfigs {
    fun fromResourceName(name : String) = when(name){
        "kitchen" -> LightSwitchConfig("Kitchen", "Kitchen",null)
        "livingroom" -> LightSwitchConfig("Living room", "Living Room", null)
        "hallway" -> LightSwitchConfig("Hallway", "Hallway", null)
        "bathroom" -> LightSwitchConfig("Bathroom", "Bathroom", null)
        "bedroom" -> LightSwitchConfig("Bedroom", "Bedroom", null)
        else -> LightSwitchConfig()
    }
}