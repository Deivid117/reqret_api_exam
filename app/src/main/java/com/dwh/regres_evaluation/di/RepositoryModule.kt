package com.dwh.gamesapp.di

import com.dwh.regres_evaluation.data.repository.DataStoreRepositoryImp
import com.dwh.regres_evaluation.data.repository.LocalUserRepositoryImp
import com.dwh.regres_evaluation.data.repository.UserRepositoryImp
import com.dwh.regres_evaluation.domain.repository.DataStoreRepository
import com.dwh.regres_evaluation.domain.repository.LocalUserRepository
import com.dwh.regres_evaluation.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsUserRepository(UserRepository: UserRepositoryImp): UserRepository

    @Binds
    abstract fun bindsLocalUserRepository(localUserRepositoryImp: LocalUserRepositoryImp): LocalUserRepository

    @Binds
    abstract fun bindsDataStoreRepository(dataStoreRepositoryImp: DataStoreRepositoryImp): DataStoreRepository

}