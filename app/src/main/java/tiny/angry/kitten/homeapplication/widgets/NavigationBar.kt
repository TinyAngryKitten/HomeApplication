package tiny.angry.kitten.homeapplication.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import tiny.angry.kitten.homeapplication.R

@ExperimentalStdlibApi
class NavigationBar(
    context : Context,
    attribs : AttributeSet
) : LinearLayout(context, attribs) {

    val pcStatsButton = buildButton("PC")
    val lightsButton = buildButton("TV remote")
    val fancontrolButton = buildButton("Fans")

    init {
        View.inflate(context, R.layout.widget_navigation_bar, this)

        addView(pcStatsButton)
        addView(lightsButton)
        addView(fancontrolButton)
    }

    private fun buildButton(buttonText: String) =
        Button(context).apply {
            text = buttonText
        }
}