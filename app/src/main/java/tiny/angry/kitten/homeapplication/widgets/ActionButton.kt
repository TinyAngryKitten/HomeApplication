package tiny.angry.kitten.homeapplication.widgets

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import tiny.angry.kitten.homeapplication.data.ActionConfig

class ActionButton(
    context: Context,
    attrs : AttributeSet,
    val config : ActionConfig
) : ImageButton(context,attrs){

    init {
        setOnClickListener(config.action)
        if(config.icon != null) setImageResource(config.icon)
        setBackgroundColor(Color.TRANSPARENT)
        //setBackgroundDrawable(null)
    }
}