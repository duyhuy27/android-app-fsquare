package vn.md18.fsquareapplication.features.profileandsetting.repositoriy

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.GetWardsRepose
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse

interface WardRepository {
    fun getWardList(id:String) : Flowable<DataResponse<List<GetWardsRepose>, PaginationResponse>>
}