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
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import javax.inject.Inject

class LocationCustomerRepositoryImpl  @Inject constructor(
    private var applicationService: ApplicationService
) : LocationCustomerRepository {
    override fun getLocationList(): Flowable<DataResponse<List<GetLocationCustomerResponse>, PaginationResponse>> {
        return applicationService.getLocationCustomer()
    }

    override fun createLocation(addLocationCustomerRequest: AddLocationCustomerRequest): Flowable<AddLocationCustomerResponse> {
        return applicationService.createLocationCustomer(addLocationCustomerRequest)
    }

    override fun updateLocation(
        id: String,
        updateLocationCustomerRequest: UpdateLocationCustomerRequest
    ): Flowable<UpdateLocationCustomerResponse> {
        return applicationService.updateLocationCustomer(id, updateLocationCustomerRequest)
    }

    override fun deleteLocation(id: String): Flowable<DeleteLocationCustomerResponse> {
        return applicationService.deleteLocationCustomer(id)
    }

}