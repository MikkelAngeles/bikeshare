package mhel.itu.moapd.bikeshare.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentUser : ViewModel() {

    val id: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

    val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val rides: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val balance: MutableLiveData<Float> by lazy {
        MutableLiveData<Float>()
    }

    val activeRide: MutableLiveData<Long?> by lazy {
        MutableLiveData<Long?>()
    }

}