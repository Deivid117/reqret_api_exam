package com.dwh.regres_evaluation.data.model.response

import com.google.gson.annotations.SerializedName

data class UpdateUserResponse(
    @SerializedName("name"      ) var name      : String? = null,
    @SerializedName("job"       ) var job       : String? = null,
    @SerializedName("updatedAt" ) var updatedAt : String? = null
)
