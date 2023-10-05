package com.dwh.regres_evaluation.domain.model.response

import com.dwh.regres_evaluation.data.model.response.UserCreatedResponse
import com.google.gson.annotations.SerializedName

data class UserCreated(
    @SerializedName("name"      ) var name      : String? = null,
    @SerializedName("job"       ) var job       : String? = null,
    @SerializedName("id"        ) var id        : String? = null,
    @SerializedName("createdAt" ) var createdAt : String? = null
)

fun UserCreatedResponse.toDomain() = UserCreated(name, job, id, createdAt)
