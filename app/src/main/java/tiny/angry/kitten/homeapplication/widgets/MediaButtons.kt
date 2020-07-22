package tiny.angry.kitten.homeapplication.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.widget_media_buttons.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.invocation.media.MediaFunction

class MediaButtons(context: Context, attrset : AttributeSet) : LinearLayout(context,attrset), KoinComponent {
    val invokeFunction  : (MediaFunction) -> Unit by inject(named("invokeMediaFunction"))

    init {
        View.inflate(context, R.layout.widget_media_buttons,this)

        previousButton.setOnClickListener { invokeFunction(MediaFunction.Previous) }
        nextButton.setOnClickListener { invokeFunction(MediaFunction.Next) }
        volUpButton.setOnClickListener { invokeFunction(MediaFunction.VolumeUp) }
        voldDownButton.setOnClickListener { invokeFunction(MediaFunction.VolumeDown) }
        togglePlayButton.setOnClickListener { invokeFunction(MediaFunction.Pause) }
    }
}