package com.dwh.regres_evaluation.data.model.response

import com.google.gson.annotations.SerializedName

data class UserTokenResponse(
    @SerializedName("token" ) var token : String
)
