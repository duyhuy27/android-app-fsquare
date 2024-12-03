package vn.md18.fsquareapplication.features.profileandsetting.repositoriy.editprofile

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.request.UpdateProfileRequest
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.UpdateProfileResponse
import vn.md18.fsquareapplication.data.network.model.response.profile.GetProfileResponse

interface EditProfileRepository {
    fun getProfile() : Flowable<DataResponse<GetProfileResponse, PaginationResponse>>
    fun updateProfile(updateProfileRequest: UpdateProfileRequest) : Flowable<UpdateProfileResponse>
}