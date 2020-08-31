package tiny.angry.kitten.homeapplication.widgets

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.findFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import kotlinx.android.synthetic.main.widget_screen_toggle.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.util.getActivity
import tiny.angry.kitten.homeapplication.util.lifecycleOwner
import tiny.angry.kitten.homeapplication.viewmodels.ScreenStateViewModel

class ScreenToggler(context : Context, attrs : AttributeSet) : LinearLayout(context,attrs), KoinComponent {
    val screenStateViewModel : ScreenStateViewModel by inject()

    init {
        inflate(context,R.layout.widget_screen_toggle,this)
        toggleButton.setBackgroundColor(Color.TRANSPARENT)
        toggleButton.setBackgroundDrawable(null)

        toggleButton.setOnClickListener {
            val isScreenAllwaysOn = screenStateViewModel.allwaysOn.value ?: false
            val window = it.context.getActivity()?.window

            if (window != null) {
                if (!isScreenAllwaysOn) window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                else window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

                screenStateViewModel.allwaysOn.value = !isScreenAllwaysOn
            }
        }

        screenStateViewModel.allwaysOn.observe({context.lifecycleOwner()?.lifecycle!!}) { screenAllwaysOn ->
            Log.d("TOG", "TOGGLE")
            toggleButton.setImageResource(
                if (screenAllwaysOn) R.mipmap.light_on_icon_foreground
                else R.mipmap.light_off_icon_foreground
            )
        }
    }
}