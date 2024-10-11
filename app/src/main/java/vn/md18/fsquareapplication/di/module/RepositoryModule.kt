package vn.md18.fsquareapplication.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import vn.md18.fsquareapplication.features.auth.repository.AuthRepository
import vn.md18.fsquareapplication.features.auth.repository.AuthRepositoryImpl
import vn.md18.fsquareapplication.features.main.repository.MainRepository
import vn.md18.fsquareapplication.features.main.repository.MainRepositoryImpl
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.ProfileRepository
import vn.md18.fsquareapplication.features.profileandsetting.repositoriy.ProfileRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun provideMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

    @Binds
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl) : AuthRepository

    @Binds
    fun provideProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl) : ProfileRepository
}