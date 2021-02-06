package tiny.angry.kitten.homeapplication.widgets

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.util.fragmentActivity
import tiny.angry.kitten.homeapplication.util.startGoogleAssistant
import tiny.angry.kitten.homeapplication.views.FanControlDialog
import tiny.angry.kitten.homeapplication.views.LightsActivityDirections
import tiny.angry.kitten.homeapplication.views.TVControlDialog

@ExperimentalStdlibApi
class NavigationBar(
    context : Context,
    attribs : AttributeSet
) : LinearLayout(context, attribs) {

    val pcStatsButton = buildButton("PC"){
        it.findNavController().navigate(LightsActivityDirections.actionLightsActivityToPCStatsActivity())
     }

    val lightsButton = buildButton("TV remote") {
        TVControlDialog().show(context.fragmentActivity()?.supportFragmentManager!!, "TVControls")
    }

    val fancontrolButton = buildButton("Fans") {
        FanControlDialog().show(context.fragmentActivity()?.supportFragmentManager!!, "Fancontrol")
    }

    val googleAssistantButton = buildButton(R.mipmap.google_icon_foreground) {
        startGoogleAssistant(context)
    }

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
        addView(googleAssistantButton)

        addView(spacerRight)
        addView(connectionIndicator)
    }

    private fun buildButton(buttonText: String, onClick: (View) -> Unit = {}) =
        Button(context).apply {
            text = buttonText
            setOnClickListener(onClick)
        }

    private fun buildButton(imgId : Int, onClick: (View) -> Unit = {}) =
        ImageButton(context).apply {
            setImageResource(imgId)
            setOnClickListener(onClick)
            setBackgroundColor(Color.TRANSPARENT)
        }
}