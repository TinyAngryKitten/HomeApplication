package tiny.angry.kitten.homeapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AirqualityViewModel : ViewModel() {
    val temperature : MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(null)
    }

    val humidity : MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(null)
    }

    val tvoc : MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(null)
    }
}