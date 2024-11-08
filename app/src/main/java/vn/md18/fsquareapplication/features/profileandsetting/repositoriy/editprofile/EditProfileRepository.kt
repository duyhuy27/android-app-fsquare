package vn.md18.fsquareapplication.features.profileandsetting.repositoriy.editprofile

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.network.model.response.profile.GetProfileResponse

interface EditProfileRepository {
    fun getProfile() : Flowable<GetProfileResponse>
}