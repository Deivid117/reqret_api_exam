package com.dwh.regres_evaluation.data.model.response

import com.google.gson.annotations.SerializedName

data class UserDataResponse(
    @SerializedName("id"    ) var id    : Int,
    @SerializedName("token" ) var token : String
)
