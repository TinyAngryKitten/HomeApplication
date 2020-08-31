package tiny.angry.kitten.homeapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.mapOf
import tiny.angry.kitten.homeapplication.data.LightState

class LightStateViewModel : ViewModel() {
    val lightState : MutableLiveData<Map<String,LightState>> by lazy {
        MutableLiveData<Map<String,LightState>>(mapOf())
    }
}