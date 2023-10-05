package com.dwh.regres_evaluation.domain.model.response

import com.dwh.gamesapp.data.database.entities.UserLoggedEntity


data class UserLogged(
    val id: Int = 0,
    val email: String = "",
    val token: String = ""
)

fun UserLoggedEntity.toDomain() = UserLogged(id, email, token)