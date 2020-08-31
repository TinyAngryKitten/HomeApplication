package tiny.angry.kitten.homeapplication.data

import tiny.angry.kitten.homeapplication.R

object ActionConfigs {
    fun fromResourceName(name : String) = when(name) {
        "leaving" -> ActionConfig(icon = R.mipmap.next_icon_foreground, name = "Im leaving")
        "home" -> ActionConfig(icon = R.mipmap.last_icon_foreground, name = "Im home")
        else -> ActionConfig()
    }
}