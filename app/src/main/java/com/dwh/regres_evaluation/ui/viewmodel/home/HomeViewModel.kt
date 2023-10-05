package com.dwh.regres_evaluation.ui.viewmodel.home

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.regres_evaluation.domain.model.response.UserLogged
import com.dwh.regres_evaluation.domain.use_cases.DeleteUserUseCase
import com.dwh.regres_evaluation.domain.use_cases.GetUserDataUseCase
import com.dwh.regres_evaluation.domain.use_cases.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    application: Application
): AndroidViewModel(application) {

    private var _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val uiState: MutableStateFlow<HomeUiState> get() = _uiState

    fun getUsers() = viewModelScope.launch {
        _uiState.value = HomeUiState.Loading
        try {
            getUsersUseCase().collect {
                _uiState.value = HomeUiState.Success(it)
            }
        } catch (e: Exception) {
            _uiState.value = HomeUiState.Error(e.message ?: "Error desconocido")
            Log.e("GetUsers", (_uiState.value as HomeUiState.Error).errorMessage)
        }
    }

    private var _userUiState: MutableStateFlow<UserUiState> = MutableStateFlow(UserUiState.Success(
        UserLogged()
    ))
    val userUiState: MutableStateFlow<UserUiState> get() = _userUiState

    fun getUserInfo() = viewModelScope.launch {
        try {
            getUserDataUseCase().collect {
                _userUiState.value = UserUiState.Success(it)
            }
        } catch (e: Exception) {
            _userUiState.value = UserUiState.Error(e.message ?: "Error desconocido")
            Log.e("GetUserInfo", e.message ?: "Error desconocido")
        }
    }

    fun deleteUser(id: Int, success: (Boolean) -> Unit) = viewModelScope.launch {
        try {
            deleteUserUseCase(id).collect {
                //Log.d("Success", it.toString())
            }
            success(true)
            Toast.makeText(getApplication(), "Se ha eliminado el usuario correctamente", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            success(false)
            Log.e("DeleteUser", e.message ?: "Error desconocido")
            Log.e("DeleteUser", "Error: ${e.message}", e)
        }
    }

}