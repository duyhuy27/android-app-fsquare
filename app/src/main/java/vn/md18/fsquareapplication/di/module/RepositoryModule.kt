package vn.md18.fsquareapplication.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import vn.md18.fsquareapplication.features.auth.repository.AuthRepository
import vn.md18.fsquareapplication.features.auth.repository.AuthRepositoryImpl
import vn.md18.fsquareapplication.features.detail.repository.DetailProductRepository
import vn.md18.fsquareapplication.features.detail.repository.DetailProductRepositoryImpl
import vn.md18.fsquareapplication.features.main.repository.MainRepository
import vn.md18.fsquareapplication.features.main.repository.MainRepositoryImpl
import vn.md18.fsquareapplication.features.main.repository.OrderRepository
import vn.md18.fsquareapplication.features.main.repository.OrderRepositoryImpl
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.DistrictRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.DistrictRepositoryImpl
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.ProfileRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.ProfileRepositoryImpl
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.ProvinceRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.ProvinceRepositoryImpl
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.WardRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.WardRepositoryImpl
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.editprofile.EditProfileRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.editprofile.EditProfileRepositoryImpl
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.location.LocationCustomerRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.location.LocationCustomerRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun provideMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

    @Binds
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository

    @Binds
    fun provideProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl) : ProfileRepository

    @Binds
    fun provideProvinceRepository(provinceRepositoryImpl: ProvinceRepositoryImpl) : ProvinceRepository

    @Binds
    fun provideDistrictRepository(districtRepositoryImpl: DistrictRepositoryImpl) : DistrictRepository

    @Binds
    fun provideWardRepository(wardRepositoryImpl: WardRepositoryImpl) : WardRepository

    @Binds
    fun provideLocationRepository(locationCustomerRepositoryImpl: LocationCustomerRepositoryImpl) : LocationCustomerRepository

    @Binds
    fun provideOrderRepository(orderRepositoryImpl: OrderRepositoryImpl) :OrderRepository

    @Binds
    fun profileRepository(editProfileRepositoryImpl: EditProfileRepositoryImpl) :EditProfileRepository

    @Binds
    fun provideDetailRepository(detailProductRepositoryImpl: DetailProductRepositoryImpl) : DetailProductRepository
}