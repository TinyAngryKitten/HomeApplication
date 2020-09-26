package tiny.angry.kitten.homeapplication.views

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Gravity
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.tv_control_view.view.*
import org.koin.core.KoinComponent
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.invocation.harmony.HarmonyInvoker
import tiny.angry.kitten.topics.harmony.HarmonyHubActions
import tiny.angry.kitten.topics.harmony.HarmonyHubDevices


class TvControlView(context: Context) : LinearLayout(context), KoinComponent {
    val harmonyController = HarmonyInvoker()

    init {
        inflate(context, R.layout.tv_control_view,this)

        tvPowerButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.TV,HarmonyHubActions.PowerToggle)
            vibrate()
        }
        appleTvPower.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.Menu)
            vibrate()
        }

        upButton.setOnClickListener{
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.DirectionUp)
            vibrate()
        }
        downButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.DirectionDown)
            vibrate()
        }
        rightButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.DirectionRight)
            vibrate()
        }
        leftButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.DirectionLeft)
            vibrate()
        }
        OKButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.Select)
            vibrate()
        }
        backButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.Back)
            vibrate()
        }
        appletvButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.TV,HarmonyHubActions.InputHdmi3)
            vibrate()
        }
        playstationButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.TV,HarmonyHubActions.InputHdmi1)
            vibrate()
        }
        plusButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.TV,HarmonyHubActions.VolumeUp)
            vibrate()
        }
        minusButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.TV,HarmonyHubActions.VolumeDown)
            vibrate()
        }
    }

    fun vibrate() {
        val vibrator =
            context.getSystemService(VIBRATOR_SERVICE) as Vibrator?
        vibrator?.vibrate(VibrationEffect.createOneShot(100L,10))
    }
}

class TVControlDialog : DialogFragment() {
    val harmonyController = HarmonyInvoker()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = LinearLayout(inflater.context)
            view.gravity = Gravity.CENTER
            val controllerView = TvControlView(inflater.context)
            view.addView(controllerView)
            builder.setView(view)
            builder.create().apply {
                show()
                window?.setLayout(590,520)
            }
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}