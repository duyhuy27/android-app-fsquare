package vn.md18.fsquareapplication.features.main.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.features.main.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : BaseViewModel() {

    override fun onDidBindViewModel() {
    }

    fun checkLoading() {
        viewModelScope.launch {
            setLoading(true)
            //delay
            delay(1000)
            setLoading(false)
        }
    }

}