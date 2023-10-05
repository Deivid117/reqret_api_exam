package com.dwh.regres_evaluation.ui.viewmodel.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.regres_evaluation.domain.use_cases.FindUserLogged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val findUserLogged: FindUserLogged
): ViewModel() {

    fun isThereLoggedUser(success: (Boolean) -> Unit) = viewModelScope.launch {
        val userLogged = findUserLogged()
        if(userLogged != null) {
            success(true)
        } else {
            success(false)
        }
    }
}