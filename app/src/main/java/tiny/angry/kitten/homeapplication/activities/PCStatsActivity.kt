package tiny.angry.kitten.homeapplication.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.pcNavButton
import kotlinx.android.synthetic.main.activity_pc_stats.*
import org.koin.androidx.viewmodel.compat.ViewModelCompat.viewModel
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.data.PCStats
import tiny.angry.kitten.homeapplication.viewmodels.PCStatsViewModel

class PCStatsActivity : AppCompatActivity(), KoinComponent {

    private val model: PCStatsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pc_stats)

        pcNavButton.setOnClickListener {
            //navigate to pc view
        }

        model.cpuStats.observe(this, Observer{
            cpuLoadText.text = "Load: ${it.load}"
            cpuTempText.text = "Temp: ${it.temp}"
        })

        model.gpuStats.observe(this, Observer {
            gpuLoadText.text = "Load: ${it.load}"
            gpuTempText.text = "Temp: ${it.temp}"
        })
    }
}