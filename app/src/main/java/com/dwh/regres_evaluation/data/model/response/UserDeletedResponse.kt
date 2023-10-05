package com.dwh.regres_evaluation.data.model.response

import com.google.gson.annotations.SerializedName

data class UserDeletedResponse(
    @SerializedName("data"    ) var data    : UserDeletedDataResponse?    = UserDeletedDataResponse(),
)
