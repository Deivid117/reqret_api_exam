package com.dwh.regres_evaluation.data.model.request

import com.dwh.regres_evaluation.domain.model.request.UserRegister
import com.google.gson.annotations.SerializedName

data class UserRegisterRequest(
    @SerializedName("email"    ) var email    : String,
    @SerializedName("password" ) var password : String
)

fun UserRegister.toData() = UserRegisterRequest(email, password)
