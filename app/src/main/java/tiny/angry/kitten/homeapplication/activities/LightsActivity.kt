package tiny.angry.kitten.homeapplication.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.activity_lights.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.invocation.lights.LightController

class LightsActivity : Fragment(), KoinComponent {
    val args : LightsActivityArgs by navArgs()

    @ExperimentalStdlibApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_lights,container,false)
    }

    @ExperimentalStdlibApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navBar.pcStatsButton.setOnClickListener {
            it.findNavController().navigate(LightsActivityDirections.actionLightsActivityToPCStatsActivity())
        }

    }
}