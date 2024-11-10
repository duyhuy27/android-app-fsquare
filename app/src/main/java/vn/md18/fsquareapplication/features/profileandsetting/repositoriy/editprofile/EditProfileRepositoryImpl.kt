package vn.md18.fsquareapplication.features.profileandsetting.repositoriy.editprofile

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.request.UpdateProfileRequest
import vn.md18.fsquareapplication.data.network.model.response.GetDistrictsResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.UpdateProfileResponse
import vn.md18.fsquareapplication.data.network.model.response.profile.GetProfileResponse
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.DistrictRepository
import javax.inject.Inject

class EditProfileRepositoryImpl @Inject constructor(
    private var applicationService: ApplicationService
) : EditProfileRepository {

    override fun getProfile(): Flowable<DataResponse<GetProfileResponse, PaginationResponse>> {
        return applicationService.getProfile()
    }

    override fun updateProfile(updateProfileRequest: UpdateProfileRequest): Flowable<UpdateProfileResponse> {
        return applicationService.updateProfile(updateProfileRequest)
    }
}