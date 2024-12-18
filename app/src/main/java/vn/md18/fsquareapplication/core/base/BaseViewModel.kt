package vn.md18.fsquareapplication.core.base

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.md18.fsquareapplication.core.eventbus.CheckPaymentStatus
import vn.md18.fsquareapplication.data.model.ErrorResponse
import vn.md18.fsquareapplication.data.network.model.request.CheckPaymentRequest
import vn.md18.fsquareapplication.di.component.datamanager.DataManager
import vn.md18.fsquareapplication.di.component.resource.ResourcesService
import vn.md18.fsquareapplication.di.component.scheduler.SchedulerProvider
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.SingleLiveEvent
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    lateinit var compositeDisposable: CompositeDisposable
    lateinit var schedulerProvider: SchedulerProvider
    lateinit var resourcesService: ResourcesService
    lateinit var dataManager: DataManager

    open val errorState = SingleLiveEvent<String>()
    open val loadingState = SingleLiveEvent<Boolean>()
    open val errorMessage = SingleLiveEvent<Int>()





    fun setLoading(isLoading: Boolean) {
        loadingState.value = isLoading
    }

    internal fun init(
        schedulerProvider: SchedulerProvider,
        resourcesService: ResourcesService,
        dataManager: DataManager
    ) {
        this.schedulerProvider = schedulerProvider
        this.resourcesService = resourcesService
        this.dataManager = dataManager
        compositeDisposable = CompositeDisposable()
    }

    open fun onDidBindViewModel()
    {
        EventBus.getDefault().register(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
        compositeDisposable.dispose()
    }

    open  fun setError(errorResponse: ErrorResponse) {
        val errorMessage = errorResponse.message

        when(errorResponse.status) {
            "Conflict" -> {

            }
            "Internal Server Error" -> {

            }
        }


    }

    fun setErrorString(errorMessage: String) {
        errorState.value = errorMessage
    }

    fun setErrorStringId(errorMessageId: Int) {
        errorMessage.value = errorMessageId
    }

    fun checkTokenIfNeeded(callBackNavigation: (Boolean) -> Unit) {
        if (dataManager.getToken().isNullOrEmpty()) {
            callBackNavigation(false)
        }
        else {
            callBackNavigation(true)
        }
    }
    private var numberPage: Int = 0

    open fun loadMoreItems() {
        numberPage += 1
    }

    open fun refreshItemList() {
        numberPage = 0
    }

    fun getCurrentPageNumber(): Int {
        return numberPage
    }

    fun setCurrentPageNumber(pageNumber: Int) {
        this.numberPage = pageNumber
    }
}