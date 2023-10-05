package com.dwh.regres_evaluation.data.model.request

import com.dwh.regres_evaluation.domain.model.request.CreateUser
import com.google.gson.annotations.SerializedName

data class CreateUserRequest(
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("job"  ) var job  : String? = null
)

fun CreateUser.toData() = CreateUserRequest(name, job)
