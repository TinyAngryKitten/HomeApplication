package tiny.angry.kitten.homeapplication.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_pc_stats.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import tiny.angry.kitten.homeapplication.R
import tiny.angry.kitten.homeapplication.viewmodels.PCStatsViewModel
import tiny.angry.kitten.homeapplication.widgets.NavigationBar
import kotlin.math.abs

class PCStatsActivity : Fragment(), KoinComponent {

    private val model: PCStatsViewModel by inject()
    private lateinit var cpuTempSet : LineDataSet
    private lateinit var cpuLoadSet : LineDataSet

    private lateinit var gpuTempSet : LineDataSet
    private lateinit var gpuLoadSet : LineDataSet

    val maxDatasetSize = 100

    @ExperimentalStdlibApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_pc_stats, container,false)
    }

    @ExperimentalStdlibApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.findViewById<NavigationBar>(R.id.navbar)?.lightsButton?.setOnClickListener {
            Log.d("teststuff", "attempting to navigate to lights")
            it.findNavController()
                .navigate(PCStatsActivityDirections.actionPCStatsActivityToLightsActivity())
        }

        prepareChart(cpuchart, "Temperature", "Load")
        prepareChart(gpuchart, "Temperature", "Load")

        gpuLoadSet = gpuchart.data.dataSets[1] as LineDataSet
        gpuTempSet = gpuchart.data.dataSets[0] as LineDataSet

        cpuLoadSet = cpuchart.data.dataSets[1] as LineDataSet
        cpuTempSet = cpuchart.data.dataSets[0] as LineDataSet

        model.cpuLoad.observe(this, Observer {
            cpuLoadText.text = "Load: $it"
            addEntryToDataSet(it,cpuLoadSet)

            cpuchart.data.notifyDataChanged()
            cpuchart.notifyDataSetChanged()
            cpuchart.invalidate()
        })
        model.cpuTemp.observe(this, Observer {
            cpuTempText.text = "Temp: $it"
            addEntryToDataSet(it,cpuTempSet)

            cpuchart.data.notifyDataChanged()
            cpuchart.notifyDataSetChanged()
            cpuchart.invalidate()
        })

        model.gpuTemp.observe(this, Observer{
            gpuTempText.text = "Temp: $it"
            addEntryToDataSet(it,gpuTempSet)

            gpuchart.data.notifyDataChanged()
            gpuchart.notifyDataSetChanged()
            gpuchart.invalidate()
        })
        model.gpuLoad.observe(this, Observer{
            gpuLoadText.text = "Load: $it"
            addEntryToDataSet(it,gpuLoadSet)

            gpuchart.data.notifyDataChanged()
            gpuchart.notifyDataSetChanged()
            gpuchart.invalidate()
        })
        model.totalVRam.observe(this,Observer{
            vramBar.max = it
            vramBar.invalidate()

        })
        model.usedVRam.observe(this,Observer{
            vramBar.progress = it
            vramBar.invalidate()

        })
        model.totalRam.observe(this,Observer{
            ramBar.max = it
            ramBar.invalidate()
        })
        model.usedRam.observe(this,Observer{
            ramBar.progress = it
            ramBar.invalidate()
        })

        gpuchart.invalidate()
        cpuchart.invalidate()
    }

    //TODO: maybe move this stuff, uh.. somewhere

    fun prepareChart(chart : LineChart, label1 : String, label2 : String) {
        chart.setBackgroundColor(Color.WHITE)
        chart.description.isEnabled = false
        chart.setTouchEnabled(false)
        chart.setDrawGridBackground(false)

        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.isEnabled = false
        //disable right axis
        chart.axisRight.isEnabled = false
        chart.axisRight.setDrawGridLines(false)
        chart.axisLeft.setDrawGridLines(false)


        chart.axisLeft.apply {
            granularity = 1f
            axisMaximum = 100f
            axisMinimum = 0f
        }

        chart.data = LineData(
            listOf(
                createDataSet(label1, Color.BLUE),
                createDataSet(label2, Color.RED)
            )
        )
    }

    fun createDataSet(label : String, color : Int) : LineDataSet =
        LineDataSet(
            listOf<Entry>(),
            label
        ).apply {
            setDrawCircles(false)
            setDrawCircleHole(false)
            setColor(color)
            setDrawValues(false)
        }

    fun addEntryToDataSet(value : Int, dataSet: LineDataSet) = addEntryToDataSet(value.toFloat(), dataSet)
    fun addEntryToDataSet(value : Float, dataSet: LineDataSet) {
        val counter = if(dataSet.values.isNotEmpty()) abs(dataSet.values.last().x+1) else 0f

        dataSet.values = mutableListOf(
            *dataSet.values.toTypedArray()
        ).plus(Entry(counter,value))

        if(dataSet.values.size > maxDatasetSize) dataSet.removeFirst()

        dataSet.notifyDataSetChanged()
    }
}