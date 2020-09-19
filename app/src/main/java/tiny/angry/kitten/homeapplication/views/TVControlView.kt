package tiny.angry.kitten.homeapplication.views

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.tv_control_view.*
import kotlinx.android.synthetic.main.tv_control_view.view.*
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.invocation.harmony.HarmonyInvoker
import tiny.angry.kitten.topics.harmony.HarmonyHubActions
import tiny.angry.kitten.topics.harmony.HarmonyHubDevices

class TvControlView(context: Context) : LinearLayout(context) {
    val harmonyController = HarmonyInvoker()

    init {
        inflate(context, R.layout.tv_control_view,this)

        tvPowerButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.TV,HarmonyHubActions.PowerToggle)
        }
        appleTvPower.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.Menu)
        }

        upButton.setOnClickListener{
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.DirectionUp)
        }
        downButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.DirectionDown)
        }
        rightButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.DirectionRight)
        }
        leftButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.DirectionLeft)
        }
        OKButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.Select)
        }
        backButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.APPLE_TV,HarmonyHubActions.Back)
        }
        appletvButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.TV,HarmonyHubActions.InputHdmi3)
        }
        playstationButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.TV,HarmonyHubActions.InputHdmi1)
        }
        plusButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.TV,HarmonyHubActions.VolumeUp)
        }
        minusButton.setOnClickListener {
            harmonyController.invokeAction(HarmonyHubDevices.TV,HarmonyHubActions.VolumeDown)
        }
    }
}

class TVControlSialog : DialogFragment() {
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