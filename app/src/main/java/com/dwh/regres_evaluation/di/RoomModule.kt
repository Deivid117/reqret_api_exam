package com.dwh.gamesapp.di

import android.content.Context
import com.dwh.gamesapp.data.database.DBUsersApp
import com.dwh.gamesapp.data.database.dao.UserLoggedDao
import com.dwh.regres_evaluation.data.database.dao.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext
        context: Context
    ): DBUsersApp{
        return DBUsersApp.newInstance(context)
    }

    @Singleton
    @Provides
    fun provideUserLoggedDao(dbUsersApp: DBUsersApp): UserLoggedDao {
        return dbUsersApp.userDao()
    }

    @Singleton
    @Provides
    fun provideUsersDao(dbUsersApp: DBUsersApp): UsersDao {
        return dbUsersApp.usersDao()
    }
}