package com.dwh.regres_evaluation.ui.viewmodel.registration

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.regres_evaluation.R
import com.dwh.regres_evaluation.domain.model.ErrorMessage
import com.dwh.regres_evaluation.domain.model.UserEmailDataStore
import com.dwh.regres_evaluation.domain.model.request.UserRegister
import com.dwh.regres_evaluation.domain.model.response.UserLogged
import com.dwh.regres_evaluation.domain.repository.DataStoreRepository
import com.dwh.regres_evaluation.domain.use_cases.AddUserUseCase
import com.dwh.regres_evaluation.domain.use_cases.UserRegistrationUseCase
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
class RegistrationViewModel @Inject constructor(
    private val userRegistrationUseCase: UserRegistrationUseCase,
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
            passwordConfirmationMsg = msg.passwordConfirmationMsg,
        ) }
    }

    private fun fieldsValidation(email: String, password: String, passwordConfirmation: String) : Boolean {
        var isValid = true

        if(email.isEmpty()) {
            isValid = false
            _messageError.update { it.copy(emailMsg = R.string.registration_msg_error_email1) }
        } else if(!email.isEmail()) {
            isValid = false
            _messageError.update { it.copy(emailMsg = R.string.registration_msg_error_email2) }
        }
        if(password.isEmpty()) {
            isValid = false
            _messageError.update { it.copy(passwordMsg = R.string.registration_msg_error_password1) }
        } else if(password.containsEmoji()) {
            _messageError.update { it.copy(passwordMsg = R.string.registration_msg_error_password2) }
            isValid = false
        } else if(password != passwordConfirmation && password.isNotEmpty()) {
            isValid = false
            _messageError.update { it.copy(passwordMsg = R.string.registration_msg_error_password3) }
        }
        if(passwordConfirmation.containsEmoji()) {
            _messageError.update { it.copy(passwordConfirmationMsg = R.string.registration_msg_error_password4) }
            isValid = false
        }
        return isValid
    }

    fun addUser(user: UserRegister, success: (Boolean) -> Unit) = viewModelScope.launch {
        try {
            if(fieldsValidation(user.email, user.password, user.passwordConfirmation ?: "")) {
                userRegistrationUseCase(user).collect {
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
            success(false)
            Toast.makeText(getApplication(), e.message ?: "Error desconocido", Toast.LENGTH_SHORT).show()
            Log.e("AddUser", e.message ?: "Error desconocido")
        }
    }

    private val _userEmailDataStore: MutableStateFlow<UserEmailDataStore> = MutableStateFlow(UserEmailDataStore())
    val userEmailDataStore: StateFlow<UserEmailDataStore> = _userEmailDataStore

    fun getUserEmail() = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.getUserEmail().collect {
            withContext(Dispatchers.Main) {
                _userEmailDataStore.value = it
            }
        }
    }
}