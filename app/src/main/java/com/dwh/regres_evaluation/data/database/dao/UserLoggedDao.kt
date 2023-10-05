package com.dwh.gamesapp.data.database.dao

import androidx.room.*
import com.dwh.gamesapp.data.database.entities.UserLoggedEntity
import com.dwh.regres_evaluation.domain.model.response.UserLogged
import kotlinx.coroutines.flow.Flow

@Dao
interface UserLoggedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userLoggedEntity: UserLoggedEntity)

    @Query("SELECT * FROM user_table WHERE token IS NOT NULL AND token != ''")
    suspend fun findUserLogged(): UserLogged?

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUserInfo(): Flow<UserLoggedEntity>
}