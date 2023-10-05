package com.dwh.gamesapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dwh.gamesapp.data.database.dao.UserLoggedDao
import com.dwh.gamesapp.data.database.entities.UserLoggedEntity
import com.dwh.regres_evaluation.data.database.dao.UsersDao
import com.dwh.regres_evaluation.data.database.entities.UsersEntity

@Database(
    entities = [
        UserLoggedEntity::class,
        UsersEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DBUsersApp : RoomDatabase() {

    abstract fun userDao(): UserLoggedDao
    abstract fun usersDao(): UsersDao

    companion object {
        @JvmStatic
        fun newInstance(context: Context): DBUsersApp {
            return Room
                .databaseBuilder(context, DBUsersApp::class.java, "DBGamesApp")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}