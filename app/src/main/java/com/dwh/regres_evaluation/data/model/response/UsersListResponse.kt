package com.dwh.regres_evaluation.data.model.response

import com.google.gson.annotations.SerializedName

data class UsersListResponse(
    @SerializedName("data"        ) var data       : ArrayList<UsersResponse> = arrayListOf(),
)
