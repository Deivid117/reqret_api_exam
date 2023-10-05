package com.dwh.regres_evaluation.data.database.dao

import androidx.room.*
import com.dwh.gamesapp.data.database.entities.UserLoggedEntity
import com.dwh.regres_evaluation.data.database.entities.UsersEntity
import com.dwh.regres_evaluation.domain.model.response.UserCreated
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(userEntity: List<UsersEntity>)

    @Query("SELECT * FROM users_api_table")
    suspend fun getAllUsers(): List<UsersEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(userEntity: UsersEntity): Long

    @Query("DELETE FROM users_api_table WHERE id = :id")
    suspend fun deleteUserFromDatabase(id: Int)
}