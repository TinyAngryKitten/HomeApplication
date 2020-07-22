package tiny.angry.kitten.homeapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tiny.angry.kitten.homeapplication.data.PCStats

class PCStatsViewModel : ViewModel() {
    val cpuStats : MutableLiveData<PCStats.CPUStats> by lazy {
        MutableLiveData<PCStats.CPUStats>()
    }

    val gpuStats : MutableLiveData<PCStats.GPUStats> by lazy {
        MutableLiveData<PCStats.GPUStats>()
    }

    val gpuTemp : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val gpuLoad : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val gpuFan : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val usedVRam : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val totalVRam : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    val cpuTemp : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val cpuLoad : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val cpuFan : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val usedRam : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val totalRam : MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
}