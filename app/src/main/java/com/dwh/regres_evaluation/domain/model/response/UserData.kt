package com.dwh.regres_evaluation.domain.model.response

import com.dwh.regres_evaluation.data.model.response.UserDataResponse
import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("id"    ) var id    : Int,
    @SerializedName("token" ) var token : String
)

fun UserDataResponse.toDomain() = UserData(id, token)