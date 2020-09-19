package tiny.angry.kitten.homeapplication.data

import android.view.View

data class ActionConfig(
    val icon : Int? = null,
    val name : String = "",
    val action : (View) -> Unit = {}
)