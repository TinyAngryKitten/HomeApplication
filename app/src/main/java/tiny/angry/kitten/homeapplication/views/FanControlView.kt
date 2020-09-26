package tiny.angry.kitten.homeapplication.views

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import io.vertx.mqtt.MqttClient
import kotlinx.android.synthetic.main.fan_control_view.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.invocation.harmony.HarmonyInvoker
import tiny.angry.kitten.topics.harmony.HarmonyHubActions
import tiny.angry.kitten.topics.harmony.HarmonyHubDevices

class FanControlView(context: Context) : LinearLayout(context) {
    val harmonyController = HarmonyInvoker()

    init {
        inflate(context, R.layout.fan_control_view,this)
        fanPowerButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.FANS,HarmonyHubActions.PowerToggle)
            vibrate()
        }
        fanrotationButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.FANS,HarmonyHubActions.Oscillation)
            vibrate()
        }
        fanspeedButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.FANS,HarmonyHubActions.Speed)
            vibrate()
        }
        fanmodeButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.FANS,HarmonyHubActions.Mode)
            vibrate()
        }

    }

    fun vibrate() {
        val vibrator =
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        vibrator?.vibrate(VibrationEffect.createOneShot(100L,10))
    }
}


class FanControlDialog : DialogFragment() {
    val harmonyController = HarmonyInvoker()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = LinearLayout(inflater.context)
            view.gravity = Gravity.CENTER
            val controllerView = FanControlView(inflater.context)
            view.addView(controllerView)
            builder.setView(view)
            builder.create().apply {
                show()
                //window?.setLayout(590,520)
            }
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}