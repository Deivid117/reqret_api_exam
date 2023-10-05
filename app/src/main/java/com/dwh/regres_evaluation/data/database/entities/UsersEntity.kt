package com.dwh.regres_evaluation.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dwh.regres_evaluation.domain.model.response.Users
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users_api_table")
data class UsersEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo("email"      ) var email     : String? = "",
    @ColumnInfo("first_name" ) var firstName : String? = "",
    @ColumnInfo("last_name"  ) var lastName  : String? = "",
    @ColumnInfo("avatar"     ) var avatar    : String? = "",
    @ColumnInfo("job"     ) var job    : String? = ""
)

fun Users.toDatabase() = UsersEntity(id, email, firstName, lastName, avatar)