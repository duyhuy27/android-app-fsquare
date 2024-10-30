package vn.md18.fsquareapplication.features.profileandsetting.repositoriy

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.GetDistrictsResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import javax.inject.Inject

class DistrictRepositoryImpl @Inject constructor(
    private var applicationService: ApplicationService
) : DistrictRepository {
    override fun getDistrictList(id:String): Flowable<DataResponse<List<GetDistrictsResponse>, PaginationResponse>> {
        return applicationService.getDistrict(id)
    }

}