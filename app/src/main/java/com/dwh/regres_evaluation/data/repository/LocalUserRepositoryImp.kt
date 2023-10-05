package com.dwh.regres_evaluation.data.repository

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.dwh.gamesapp.data.database.dao.UserLoggedDao
import com.dwh.gamesapp.data.database.entities.toDatabase
import com.dwh.regres_evaluation.domain.model.response.UserLogged
import com.dwh.regres_evaluation.domain.model.response.toDomain
import com.dwh.regres_evaluation.domain.repository.LocalUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.sql.SQLException
import javax.inject.Inject

class LocalUserRepositoryImp @Inject constructor(
    private val userLoggedDao: UserLoggedDao
): LocalUserRepository {
    override suspend fun insertUser(user: UserLogged) {
        userLoggedDao.insertUser(user.toDatabase())
    }

    @SuppressLint("LongLogTag")
    override suspend fun findUserLogged(): UserLogged? {
        return try {
            userLoggedDao.findUserLogged()
        } catch (e: SQLiteConstraintException) {
            Log.e("FindeUserLogged_SQLiteConstraintException", e.message ?: "Error desconocido")
            throw Exception("SQLiteConstraintException ${e.message ?: "Error desconocido"}")

        } catch (e: SQLException) {
            Log.e("FindeUserLogged_SQLException",e.message ?: "Error desconocido")
            throw Exception("SQLException ${e.message ?: "Error desconocido"}")
        }
    }

    override suspend fun deleteAll() {
        userLoggedDao.deleteAll()
    }

    @SuppressLint("LongLogTag")
    override suspend fun getUserInfo(): Flow<UserLogged> {
        try {
            val user =  userLoggedDao.getUserInfo()
            return user.map { it.toDomain() }
        } catch (e: SQLiteConstraintException) {
            Log.e("UserInfo_SQLiteConstraintException", e.message ?: "Error desconocido")
            throw Exception("SQLiteConstraintException ${e.message ?: "Error desconocido"}")

        } catch (e: SQLException) {
            Log.e("UserInfo_SQLException",e.message ?: "Error desconocido")
            throw Exception("SQLException ${e.message ?: "Error desconocido"}")
        }
    }

}