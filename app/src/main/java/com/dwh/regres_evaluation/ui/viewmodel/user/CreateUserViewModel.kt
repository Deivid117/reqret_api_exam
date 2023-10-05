package com.dwh.regres_evaluation.ui.viewmodel.user

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.regres_evaluation.R
import com.dwh.regres_evaluation.domain.model.ErrorMessage
import com.dwh.regres_evaluation.domain.model.request.CreateUser
import com.dwh.regres_evaluation.domain.use_cases.CreateUserUseCase
import com.dwh.regres_evaluation.utils.StringRegex.containsEmoji
import com.dwh.regres_evaluation.utils.StringRegex.isLetter
import com.dwh.regres_evaluation.utils.StringRegex.isNumber
import com.dwh.regres_evaluation.utils.StringRegex.isSpecialCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    application: Application
): AndroidViewModel(application) {

    private val _messageError: MutableStateFlow<ErrorMessage> = MutableStateFlow(ErrorMessage())
    val messageError: StateFlow<ErrorMessage> get() = _messageError

    fun setValuesMessageError(msg: ErrorMessage) {
        _messageError.update { it.copy(
            nameMsg = msg.nameMsg,
            jobMsg = msg.jobMsg,
        ) }
    }

    private fun fieldsValidation(name: String, job: String) : Boolean {
        var isValid = true

        if(name.isEmpty()) {
            isValid = false
            _messageError.update { it.copy(nameMsg = R.string.create_msg_error_name1) }
        } else if(name.containsEmoji()) {
            _messageError.update { it.copy(nameMsg = R.string.create_msg_error_name2) }
            isValid = false
        } else if(name.isNumber()){
            _messageError.update { it.copy(nameMsg = R.string.edit_msg_error_name3) }
            isValid = false
        } else if(name.isSpecialCharacters()){
            _messageError.update { it.copy(nameMsg = R.string.create_msg_error_name3) }
            isValid = false
        } else if(!name.isLetter()) {
            _messageError.update { it.copy(nameMsg = R.string.create_msg_error_name3) }
            isValid = false
        }
        if(job.isEmpty()) {
            isValid = false
            _messageError.update { it.copy(jobMsg = R.string.create_msg_error_job1) }
        } else if(job.containsEmoji()) {
            _messageError.update { it.copy(jobMsg = R.string.create_msg_error_job2) }
            isValid = false
        } else if(job.isNumber()){
            _messageError.update { it.copy(jobMsg = R.string.create_msg_error_job3) }
            isValid = false
        } else if(job.isSpecialCharacters()){
            _messageError.update { it.copy(jobMsg = R.string.create_msg_error_job3) }
            isValid = false
        } else if(!job.isLetter()) {
            _messageError.update { it.copy(jobMsg = R.string.create_msg_error_job3) }
            isValid = false
        }
        return isValid
    }

    fun createUser(user: CreateUser, success: (Boolean) -> Unit) = viewModelScope.launch {
        try {
            if(fieldsValidation(user.name ?: "", user.job ?: "")) {
                createUserUseCase(user).collect {
                    Log.d("Success", it.toString())
                }
                success(true)
                Toast.makeText(getApplication(), "Usuario creado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(getApplication(), "Comprueba los datos ingresados", Toast.LENGTH_SHORT).show()
                success(false)
            }
        } catch (e: Exception) {
            success(false)
            Log.e("CreateUser", e.message ?: "Error desconocido")
            Toast.makeText(getApplication(), e.message ?:"Error desconocido", Toast.LENGTH_SHORT).show()

        }
    }

}