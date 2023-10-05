package com.dwh.regres_evaluation.data.model.request

import com.dwh.regres_evaluation.domain.model.request.UserLogin
import com.google.gson.annotations.SerializedName

data class UserLoginRequest(
    @SerializedName("email"    ) var email    : String,
    @SerializedName("password" ) var password : String
)

fun UserLogin.toData() = UserLoginRequest(email, password)