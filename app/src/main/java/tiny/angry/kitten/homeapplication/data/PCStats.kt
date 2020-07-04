package tiny.angry.kitten.homeapplication.data

sealed class PCStats {
    data class GPUStats(val load: Int, val temp: Int, val usedVRam: Int,val maxVRam: Int) : PCStats()
    data class CPUStats(val load: Int, val temp: Int,val usedRam: Int,val maxRam: Int) : PCStats()
}