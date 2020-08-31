package tiny.angry.kitten.homeapplication.data

data class LightState(
    val brightness : Int = 0,
    val state : Boolean = false,
    val color : String = ""
)