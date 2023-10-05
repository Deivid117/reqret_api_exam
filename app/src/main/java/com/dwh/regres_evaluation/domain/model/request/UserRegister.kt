package com.dwh.regres_evaluation.domain.model.request

import com.dwh.regres_evaluation.data.model.request.UserRegisterRequest
import com.google.gson.annotations.SerializedName

data class UserRegister(
    @SerializedName("email"    ) var email    : String,
    @SerializedName("password" ) var password : String,
    val passwordConfirmation: String? = null,
)