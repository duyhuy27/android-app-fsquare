package vn.md18.fsquareapplication.features.profileandsetting.repositoriy.location

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.request.location.AddLocationCustomerRequest
import vn.md18.fsquareapplication.data.network.model.request.location.UpdateLocationCustomerRequest
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.location.AddLocationCustomerResponse
import vn.md18.fsquareapplication.data.network.model.response.location.DeleteLocationCustomerResponse
import vn.md18.fsquareapplication.data.network.model.response.location.GetLocationCustomerResponse
import vn.md18.fsquareapplication.data.network.model.response.location.UpdateLocationCustomerResponse

interface LocationCustomerRepository {
    fun getLocationList() : Flowable<DataResponse<List<GetLocationCustomerResponse>, PaginationResponse>>

    fun createLocation(addLocationCustomerRequest: AddLocationCustomerRequest) : Flowable<AddLocationCustomerResponse>

    fun updateLocation(id: String, updateLocationCustomerRequest: UpdateLocationCustomerRequest) : Flowable<UpdateLocationCustomerResponse>

    fun deleteLocation(id: String) : Flowable<DeleteLocationCustomerResponse>
}