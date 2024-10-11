package vn.md18.fsquareapplication.features.profileandsetting.repositoriy

import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val applicationService: ApplicationService
) : ProfileRepository{

}