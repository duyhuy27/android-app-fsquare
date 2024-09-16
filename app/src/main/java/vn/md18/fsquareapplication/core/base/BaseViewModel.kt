package vn.md18.fsquareapplication.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import vn.md18.fsquareapplication.di.component.datamanager.DataManager
import vn.md18.fsquareapplication.di.component.resource.ResourcesService
import vn.md18.fsquareapplication.di.component.scheduler.SchedulerProvider

abstract class BaseViewModel : ViewModel() {

    lateinit var compositeDisposable: CompositeDisposable
    lateinit var schedulerProvider: SchedulerProvider
    lateinit var resourcesService: ResourcesService
    lateinit var dataManager: DataManager

    open val errorState = MutableLiveData<String>()
    open val loadingState = MutableLiveData<Boolean>()


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

    abstract fun onDidBindViewModel()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}