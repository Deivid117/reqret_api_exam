package com.dwh.regres_evaluation.domain.model.response

import android.os.Parcelable
import com.dwh.regres_evaluation.data.database.entities.UsersEntity
import com.dwh.regres_evaluation.data.model.response.UsersResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    @SerializedName("id"         ) var id        : Int,
    @SerializedName("email"      ) var email     : String? = null,
    @SerializedName("first_name" ) var firstName : String? = null,
    @SerializedName("last_name"  ) var lastName  : String? = null,
    @SerializedName("avatar"     ) var avatar    : String? = null,
): Parcelable

fun UsersResponse.toDomain() = Users(id, email, firstName, lastName, avatar)

fun UsersEntity.toDomain() = Users(id, email, firstName, lastName, avatar)
