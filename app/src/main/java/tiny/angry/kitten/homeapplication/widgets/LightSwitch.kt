package tiny.angry.kitten.homeapplication.widgets

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.lightswitch_layout.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.data.LightState
import tiny.angry.kitten.homeapplication.data.LightSwitchConfigs
import tiny.angry.kitten.homeapplication.invocation.lights.LightController
import tiny.angry.kitten.homeapplication.util.lifecycleOwner
import tiny.angry.kitten.homeapplication.viewmodels.LightStateViewModel

class LightSwitch(context: Context, attributes : AttributeSet) : LinearLayout(context,attributes), KoinComponent {

    val viewmodel : LightStateViewModel by inject()

    val resourcename = context.resources.getResourceEntryName(id)
    val config = LightSwitchConfigs.fromResourceName(resourcename)

    private val lightController: LightController by inject()

    init {
        try {
            lightController.updateLocalStateOfGroup(config.lightGroup)
        } catch (e : Exception) {Log.e("Hue", e.message?:"")}

        inflate(context, R.layout.lightswitch_layout,this)

        switch1.showThumb = true
        switch1.clickToSetProgress = true
        switch1.useThumbToSetProgress = true
        switch1.thumbPlaceholderDrawable= ColorDrawable(Color.TRANSPARENT)
        switch1.setOnReleaseListener(::adjustBrightness)

        label.text = config.name

        imageView.setImageResource(R.mipmap.light_on_icon_foreground)

        context.lifecycleOwner()?.let {
            lifecycleOwner->
            viewmodel.lightState.observe({lifecycleOwner.lifecycle}) {
                stateMap : Map<String, LightState> ->
                val newBrightness = stateMap[config.lightGroup]?.brightness
                if(newBrightness != null) switch1.progress = newBrightness
            }
        }
    }

    private fun adjustBrightness(brightness: Int) {
        if(config.lightGroup.isEmpty()) return

        try {
            Log.i("Mqtt", "adjusting brightness to $brightness")
            lightController.adjustBrightnessOfGroup(config.lightGroup, brightness)
        } catch (e : Exception) {
            Log.e("Hue", "Unable to adjust brightness of group ${config.lightGroup}")
        }
    }
}