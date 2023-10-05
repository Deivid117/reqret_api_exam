package com.dwh.regres_evaluation.ui.viewmodel.login

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.regres_evaluation.R
import com.dwh.regres_evaluation.domain.model.ErrorMessage
import com.dwh.regres_evaluation.domain.model.request.UserLogin
import com.dwh.regres_evaluation.domain.model.response.UserLogged
import com.dwh.regres_evaluation.domain.repository.DataStoreRepository
import com.dwh.regres_evaluation.domain.use_cases.AddUserUseCase
import com.dwh.regres_evaluation.domain.use_cases.UserLoginUseCase
import com.dwh.regres_evaluation.utils.StringRegex.containsEmoji
import com.dwh.regres_evaluation.utils.StringRegex.isEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userLoginUseCase: UserLoginUseCase,
    private val dataStoreRepository: DataStoreRepository,
    private val addUserUseCase: AddUserUseCase,
    application: Application
): AndroidViewModel(application) {

    private val _messageError: MutableStateFlow<ErrorMessage> = MutableStateFlow(ErrorMessage())
    val messageError: StateFlow<ErrorMessage> get() = _messageError

    fun setValuesMessageError(msg: ErrorMessage) {
        _messageError.update { it.copy(
            emailMsg = msg.emailMsg,
            passwordMsg = msg.passwordMsg,
        ) }
    }

    private fun fieldsValidation(email: String, password: String) : Boolean {
        var isValid = true

        if(email.isEmpty()) {
            _messageError.update { it.copy(emailMsg = R.string.login_error_msg_email1) }
            isValid = false
        } else if(!email.isEmail()) {
            _messageError.update { it.copy(emailMsg = R.string.login_msg_error_email2) }
            isValid = false
        }
        if(password.isEmpty()) {
            _messageError.update { it.copy(passwordMsg = R.string.login_msg_error_password1) }
            isValid = false
        } else if(password.containsEmoji()) {
            _messageError.update { it.copy(passwordMsg = R.string.login_error_msg_password2) }
            isValid = false
        }
        return isValid
    }

    fun login(user: UserLogin, success: (Boolean) -> Unit) = viewModelScope.launch {
        try {
            if(fieldsValidation(user.email, user.password)) {
                userLoginUseCase(user).collect {
                    withContext(Dispatchers.IO) {
                        addUserUseCase(UserLogged(email = user.email, token = it.token))
                    }
                }
                success(true)
            } else {
                Toast.makeText(getApplication(), "Comprueba los datos ingresados", Toast.LENGTH_SHORT).show()
                success(false)
            }
        } catch (e: Exception) {
            Toast.makeText(getApplication(), e.message ?: "Error desconocido", Toast.LENGTH_SHORT).show()
            Log.e("Login", e.message ?: "Error desconocido")
        }
    }

    fun setUserEmail(email: String) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.setUserEmail(email)
    }
}