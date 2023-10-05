package com.dwh.regres_evaluation.ui.viewmodel.logout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.regres_evaluation.domain.use_cases.UserLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val logoutUseCase: UserLogoutUseCase
): ViewModel() {

    fun logout(success: (Boolean) -> Unit) = viewModelScope.launch {
        try {
            logoutUseCase()
            success(true)
        } catch (e: Exception) {
            Log.e("Logout",e.message ?: "Error desconocido")
        }
    }
}