package vn.md18.fsquareapplication.features.profileandsetting.repositoriy

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.GetDistrictsResponse
import vn.md18.fsquareapplication.data.network.model.response.GetProvinceResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse

interface DistrictRepository {
    fun getDistrictList(id:String) : Flowable<DataResponse<List<GetDistrictsResponse>, PaginationResponse>>
}