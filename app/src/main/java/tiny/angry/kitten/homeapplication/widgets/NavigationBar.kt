package tiny.angry.kitten.homeapplication.widgets

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.startActivity
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.activities.LightsActivity
import tiny.angry.kitten.homeapplication.activities.MainActivity

@ExperimentalStdlibApi
class NavigationBar(
    context : Context,
    attribs : AttributeSet
) : LinearLayout(context, attribs) {

    init {
        View.inflate(context, R.layout.widget_navigation_bar, this)

        addView(buildButton("PC", MainActivity::class.java))
        addView(buildButton("Lights", LightsActivity::class.java))
    }

    private fun <T: Activity> buildButton(buttonText: String, destination: Class<T>) =
        Button(context).apply {
            text = buttonText
            setOnClickListener {
                val intent = Intent(context, destination)
                startActivity(context,intent,null)
            }
        }
}