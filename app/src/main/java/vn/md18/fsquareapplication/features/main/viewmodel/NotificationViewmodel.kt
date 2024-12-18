package vn.md18.fsquareapplication.features.main.viewmodel

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.network.model.response.NotificationResponse
import vn.md18.fsquareapplication.features.main.repository.NotificationRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import javax.inject.Inject

@HiltViewModel
class NotificationViewmodel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val networkExtensions: NetworkExtensions
) : BaseViewModel() {

    private val _listNotification = MutableLiveData<List<NotificationResponse>>()
    val listNotification: MutableLiveData<List<NotificationResponse>> = _listNotification


    fun getListNotification() {
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    notificationRepository.getNotificationList()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            _listNotification.value = response.data ?: emptyList()
                            setLoading(false)
                        }, {
                            setLoading(false)
                        })
                )
            }
            else {
                setErrorString("No internet connection")
                setLoading(false)
            }
        }
    }

    override fun onDidBindViewModel() {

    }
}