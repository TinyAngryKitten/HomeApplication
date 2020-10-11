package tiny.angry.kitten.homeapplication.widgets

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named


class ConnectionIndicator : ImageView, KoinComponent {

    constructor(context: Context,attrs: AttributeSet): super(context,attrs)
    constructor(context: Context) : super(context)

    val stateFlow : StateFlow<Boolean> by inject<MutableStateFlow<Boolean>>(named("connectionState"))

    init {
        changeColor(stateFlow.value)

        MainScope().launch {
            stateFlow.collect {
                changeColor(it)
                invalidate()
            }
        }
    }

    private fun changeColor(state:Boolean) {
        val newColor = if(state) Color.GREEN else Color.RED
        background = drawCircle(newColor)
    }

    private fun drawCircle(color: Int): GradientDrawable? {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.OVAL
        shape.cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
        shape.setColor(color)
        shape.setStroke(10, color)
        return shape
    }
}