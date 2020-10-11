package tiny.angry.kitten.homeapplication.widgets

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import com.google.android.material.animation.DrawableAlphaProperty
import tiny.angry.kitten.homeapplication.R

@ExperimentalStdlibApi
class NavigationBar(
    context : Context,
    attribs : AttributeSet
) : LinearLayout(context, attribs) {

    val pcStatsButton = buildButton("PC")
    val lightsButton = buildButton("TV remote")
    val fancontrolButton = buildButton("Fans")
    val connectionIndicator = ConnectionIndicator(context).apply {
        layoutParams = LayoutParams(20, 20).apply {
            marginEnd = 20
        }
    }

    val spacerLeft = TextView(context).apply {
        layoutParams = LayoutParams(LayoutParams.FILL_PARENT, 1,1f)
    }
    val spacerRight = TextView(context).apply {
        layoutParams = LayoutParams(LayoutParams.FILL_PARENT, 1,1f)
    }

    init {
        View.inflate(context, R.layout.widget_navigation_bar, this)

        addView(spacerLeft)

        addView(pcStatsButton)
        addView(lightsButton)
        addView(fancontrolButton)

        addView(spacerRight)
        addView(connectionIndicator)
    }

    private fun buildButton(buttonText: String) =
        Button(context).apply {
            text = buttonText
        }
}