package com.dwh.regres_evaluation.domain.model.response

import com.dwh.regres_evaluation.data.model.response.UserDeletedDataResponse
import com.google.gson.annotations.SerializedName

data class UserDeletedData(
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("email"      ) var email     : String? = null,
    @SerializedName("first_name" ) var firstName : String? = null,
    @SerializedName("last_name"  ) var lastName  : String? = null,
    @SerializedName("avatar"     ) var avatar    : String? = null
)

fun UserDeletedDataResponse.toDomain() = UserDeletedData(id, email, firstName, lastName, avatar)