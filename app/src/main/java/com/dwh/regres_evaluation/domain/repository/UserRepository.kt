package com.dwh.regres_evaluation.domain.repository

import com.dwh.regres_evaluation.data.database.entities.UsersEntity
import com.dwh.regres_evaluation.domain.model.request.CreateUser
import com.dwh.regres_evaluation.domain.model.request.UpdateUser
import com.dwh.regres_evaluation.domain.model.request.UserLogin
import com.dwh.regres_evaluation.domain.model.request.UserRegister
import com.dwh.regres_evaluation.domain.model.response.*
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun userLogin(userLogin: UserLogin): Flow<UserToken>

    suspend fun userRegister(userRegister: UserRegister): Flow<UserData>

    suspend fun getUsersFromApi(): Flow<List<Users>>

    suspend fun getUsersFromDatabase(): Flow<List<Users>>

    suspend fun getAllUsers(): Flow<List<Users>>

    suspend fun insertUsers(usersEntity: List<UsersEntity>)

    suspend fun updateUser(id: Int, user: UpdateUser): Flow<UserUpdated>

    suspend fun createUserApi(user: CreateUser): Flow<UserCreated>

    suspend fun createUserDatabase(userEntity: UsersEntity): Long

    suspend fun createUser(user: CreateUser): Flow<UserCreated>

    suspend fun deleteUserFromApi(id: Int): Flow<UserDeletedData>

    suspend fun deleteUserFromDatabase(id: Int)

    suspend fun deleteUser(id: Int): Flow<UserDeletedData>
}