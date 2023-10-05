package com.dwh.regres_evaluation.domain.model.response

import com.dwh.regres_evaluation.data.model.response.UpdateUserResponse
import com.google.gson.annotations.SerializedName

data class UserUpdated(
    @SerializedName("name"      ) var name      : String? = null,
    @SerializedName("job"       ) var job       : String? = null,
    @SerializedName("updatedAt" ) var updatedAt : String? = null
)

fun UpdateUserResponse.toDomain() = UserUpdated(name, job, updatedAt)
