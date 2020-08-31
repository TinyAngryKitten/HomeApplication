package tiny.angry.kitten.homeapplication.util

import android.content.Context
import android.os.PowerManager
import android.util.Log
import org.koin.core.KoinComponent
import org.koin.core.inject
import tiny.angry.kitten.homeapplication.viewmodels.ScreenStateViewModel


object ScreenUtil : KoinComponent {

    val viewModel : ScreenStateViewModel by inject()

    fun wakeScreen() {
        if(!viewModel.wakelock.isHeld)
            viewModel.wakelock.acquire()
    }

    fun releaseScreen() {
        viewModel.wakelock.release()
    }
}