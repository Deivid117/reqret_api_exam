package com.dwh.regres_evaluation.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dwh.regres_evaluation.domain.model.UserEmailDataStore
import com.dwh.regres_evaluation.domain.repository.DataStoreRepository
import com.dwh.regres_evaluation.utils.Constants
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = Constants.DATA_STORE_NAME)

class DataStoreRepositoryImp(
    context: Context
) : DataStoreRepository {

    private val pref = context.dataStore

    companion object {
        var userEmail = stringPreferencesKey(Constants.USER_EMAIL)
    }

    override suspend fun setUserEmail(email: String) {
        pref.edit {preferences ->
            preferences[userEmail] = email
        }
    }

    override suspend fun getUserEmail() = pref.data.map { preferences ->
        UserEmailDataStore(
            email = preferences[userEmail].orEmpty()
        )
    }
}