package com.dwh.regres_evaluation.domain.model.request

import com.dwh.regres_evaluation.data.model.request.UpdateUserRequest
import com.google.gson.annotations.SerializedName

data class UpdateUser(
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("job"  ) var job  : String? = null
)