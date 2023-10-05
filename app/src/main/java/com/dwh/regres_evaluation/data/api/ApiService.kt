package com.dwh.gamesapp.data.api

import com.dwh.regres_evaluation.data.model.request.CreateUserRequest
import com.dwh.regres_evaluation.data.model.request.UpdateUserRequest
import com.dwh.regres_evaluation.data.model.request.UserLoginRequest
import com.dwh.regres_evaluation.data.model.request.UserRegisterRequest
import com.dwh.regres_evaluation.data.model.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // USER LOGIN
    @POST("login")
    suspend fun userLogin(
        @Body user: UserLoginRequest
    ): Response<UserTokenResponse>

    // USER REGISTRATION
    @POST("register")
    suspend fun registerUser(
        @Body user: UserRegisterRequest
    ): Response<UserDataResponse>

    // USERS LIST
    @GET("users")
    suspend fun getUsers(
        @Query("per_page") pageSize: Int
    ): Response<UsersListResponse>

    // USER UPDATE
    @PUT("users/{id}")
    suspend fun userUpdate(
        @Path("id") id: Int,
        @Body user: UpdateUserRequest
    ): Response<UpdateUserResponse>

    // CREATE USER
    @POST("users")
    suspend fun createUser(
        @Body user: CreateUserRequest
    ): Response<UserCreatedResponse>

    // DELETE USER
    @DELETE("users/{id}")
    suspend fun deleteUser(
        @Path("id") id: Int
    ): Response<UserDeletedResponse>
}