package tiny.angry.kitten.homeapplication.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.data.ActionConfig
import tiny.angry.kitten.homeapplication.data.ActionConfigs

class ActionGrid(context : Context, attr : AttributeSet): GridLayout(context,attr) {

    val actions : List<ActionConfig> = listOf(
        ActionConfigs.fromResourceName("home"),
        ActionConfigs.fromResourceName("leaving")
    )

    init {
        actions.map { addView(ActionButton(context,attr,it)) }
    }
}