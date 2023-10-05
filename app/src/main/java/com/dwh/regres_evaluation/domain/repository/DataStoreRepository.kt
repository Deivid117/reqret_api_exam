package com.dwh.regres_evaluation.domain.repository

import com.dwh.regres_evaluation.domain.model.UserEmailDataStore
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun setUserEmail(email: String)

    suspend fun getUserEmail(): Flow<UserEmailDataStore>
}