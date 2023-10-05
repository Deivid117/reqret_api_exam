package com.dwh.regres_evaluation.data.model.response

import com.google.gson.annotations.SerializedName

data class UserCreatedResponse(
    @SerializedName("name"      ) var name      : String? = null,
    @SerializedName("job"       ) var job       : String? = null,
    @SerializedName("id"        ) var id        : String? = null,
    @SerializedName("createdAt" ) var createdAt : String? = null
)
