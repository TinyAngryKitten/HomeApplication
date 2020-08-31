package tiny.angry.kitten.homeapplication.data

import android.view.View

data class ActionConfig(
    val action : (View) -> Unit = {},
    val icon : Int? = null,
    val name : String = ""
)