package vn.md18.fsquareapplication.features.profileandsetting.repositoriy

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.GetWardsRepose
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import javax.inject.Inject

class WardRepositoryImpl @Inject constructor(
    private var applicationService: ApplicationService
    ) : WardRepository {
        override fun getWardList(id:String): Flowable<DataResponse<List<GetWardsRepose>, PaginationResponse>> {
            return applicationService.getWard(id)
        }
}