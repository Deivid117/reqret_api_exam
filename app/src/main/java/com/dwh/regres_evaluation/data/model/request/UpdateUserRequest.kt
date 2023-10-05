package com.dwh.regres_evaluation.data.model.request

import com.dwh.regres_evaluation.domain.model.request.UpdateUser
import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("job"  ) var job  : String? = null
)

fun UpdateUser.toData() = UpdateUserRequest(name, job)