package vn.md18.fsquareapplication.di.module
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable
import vn.md18.fsquareapplication.di.component.datamanager.DataManager
import vn.md18.fsquareapplication.di.component.datamanager.DataManagerImpl
import vn.md18.fsquareapplication.di.component.resource.ResourcesService
import vn.md18.fsquareapplication.di.component.resource.ResourcesServiceImpl
import vn.md18.fsquareapplication.di.component.scheduler.SchedulerProvider
import vn.md18.fsquareapplication.di.component.scheduler.SchedulerProviderImpl
import vn.md18.fsquareapplication.di.component.sharepref.AppPreference
import vn.md18.fsquareapplication.di.component.sharepref.AppPreferenceImpl
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providerAppPreference(appPreferenceImpl: AppPreferenceImpl): AppPreference {
        return appPreferenceImpl
    }

    @Provides
    @Singleton
    fun providerSchedulerProvider(schedulerProviderImpl: SchedulerProviderImpl): SchedulerProvider {
        return schedulerProviderImpl
    }

    @Provides
    @Singleton
    fun providerResourceServices(resourcesServiceImpl: ResourcesServiceImpl): ResourcesService {
        return resourcesServiceImpl
    }

    @Provides
    @Singleton
    fun providerDataManager(dataManagerImpl: DataManagerImpl): DataManager {
        return dataManagerImpl
    }

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()
}