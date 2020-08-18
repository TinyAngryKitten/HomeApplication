package tiny.angry.kitten.homeapplication.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_lights.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.pcNavButton
import kotlinx.android.synthetic.main.activity_pc_stats.*
import org.koin.androidx.viewmodel.compat.ViewModelCompat.viewModel
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.data.PCStats
import tiny.angry.kitten.homeapplication.invocation.lights.LightController
import tiny.angry.kitten.homeapplication.viewmodels.PCStatsViewModel

class LightsActivity : AppCompatActivity(), KoinComponent {
    val lightController: LightController by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lights)

        homeLights.setOnReleaseListener { progressValue ->
            Log.i("Mqtt", "adjusting brightness to $progressValue")
            lightController.adjustBrightnessOfGroup("kitchen",progressValue)
        }
    }
}