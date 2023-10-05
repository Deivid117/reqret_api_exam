package com.dwh.regres_evaluation.domain.repository

import com.dwh.regres_evaluation.domain.model.response.UserLogged
import kotlinx.coroutines.flow.Flow

interface LocalUserRepository {

    suspend fun insertUser(user: UserLogged)

    suspend fun findUserLogged(): UserLogged?

    suspend fun deleteAll()

    suspend fun getUserInfo(): Flow<UserLogged>

}