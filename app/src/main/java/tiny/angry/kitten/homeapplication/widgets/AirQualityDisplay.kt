package tiny.angry.kitten.homeapplication.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import kotlinx.android.synthetic.main.air_quality_view.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.util.lifecycleOwner
import tiny.angry.kitten.homeapplication.viewmodels.AirqualityViewModel

class AirQualityDisplay(context : Context, attrs: AttributeSet) : LinearLayout(context,attrs), KoinComponent{
    val viewmodel: AirqualityViewModel by inject()

    init {
        inflate(context, R.layout.air_quality_view,this)

        temperatureText.text = "Temperature: -- C"
        humidityText.text = "Humidity: -- %"
        tvocText.text = "Air quality: UNKNOWN"

        context.lifecycleOwner()?.lifecycle?.let { lifecycle ->
            viewmodel.humidity.observe({ lifecycle }) {
                humidityText.text = "Humidity: ${if (it != null) it else "--"}%"
            }
            viewmodel.temperature.observe({lifecycle }) {
                temperatureText.text = "Temperature: ${if (it != null) it else "--"} C"
            }
            viewmodel.tvoc.observe({ lifecycle }) {
                tvocText.text = "Air quality: ${if (it != null) it else "UNKNOWN"}"
            }
        }
    }
}