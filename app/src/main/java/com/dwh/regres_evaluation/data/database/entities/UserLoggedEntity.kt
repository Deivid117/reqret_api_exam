package com.dwh.gamesapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dwh.regres_evaluation.domain.model.response.UserLogged

@Entity(tableName = "user_table")
data class UserLoggedEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "token") val token: String,
)

fun UserLogged.toDatabase() = UserLoggedEntity(id, email, token)