package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Single
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import vn.md18.fsquareapplication.di.component.datamanager.DataManager
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val applicationServices: ApplicationService,
    private val dataManager: DataManager,
) : MainRepository {
    override fun getProductList(): Single<Boolean> {
        print("")
        return Single.just(true)
    }
}