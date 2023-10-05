package com.dwh.regres_evaluation.ui.viewmodel.home

import com.dwh.regres_evaluation.domain.model.response.UserLogged

sealed class UserUiState {
    data class Success(val data: UserLogged) : UserUiState()
    data class Error(val errorMessage: String) : UserUiState()
}