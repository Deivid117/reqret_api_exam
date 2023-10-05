package com.dwh.regres_evaluation.ui.viewmodel.home

import com.dwh.regres_evaluation.domain.model.response.Users

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val data: List<Users>) : HomeUiState()
    data class Error(val errorMessage: String) : HomeUiState()
}
