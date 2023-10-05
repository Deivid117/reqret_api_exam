package com.dwh.regres_evaluation.domain.model.response

import com.dwh.regres_evaluation.data.model.response.UserTokenResponse
import com.google.gson.annotations.SerializedName

data class UserToken(
    @SerializedName("token" ) var token : String
)

fun UserTokenResponse.toDomain() = UserToken(token)