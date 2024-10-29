package vn.md18.fsquareapplication.features.profileandsetting.repositoriy

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.GetProvinceResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import javax.inject.Inject

class ProvinceRepositoryImpl @Inject constructor(
    private var applicationService: ApplicationService
) : ProvinceRepository {
    override fun getProvinceList(): Flowable<DataResponse<List<GetProvinceResponse>, PaginationResponse>> {
        return applicationService.getProvinces()
    }

}