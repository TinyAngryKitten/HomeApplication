package tiny.angry.kitten.homeapplication.viewmodels

import android.content.Context
import android.os.PowerManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScreenStateViewModel(context: Context) : ViewModel() {
    val allwaysOn : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val wakelock : PowerManager.WakeLock by lazy {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager

        powerManager.newWakeLock(
            PowerManager.FULL_WAKE_LOCK or
                    PowerManager.ACQUIRE_CAUSES_WAKEUP or
                    PowerManager.ON_AFTER_RELEASE, "appname::WakeLock"
        )
    }
}