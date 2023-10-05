package com.dwh.regres_evaluation.domain.model.request

import com.google.gson.annotations.SerializedName

data class CreateUser(
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("job"  ) var job  : String? = null
)
