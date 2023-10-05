package com.dwh.regres_evaluation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dwh.regres_evaluation.R
import com.dwh.regres_evaluation.domain.model.ErrorMessage
import com.dwh.regres_evaluation.domain.model.request.UserLogin
import com.dwh.regres_evaluation.ui.composables.BackgroundGradient
import com.dwh.regres_evaluation.ui.composables.CustomButton
import com.dwh.regres_evaluation.ui.composables.CustomOutlinedTextField
import com.dwh.regres_evaluation.ui.viewmodel.login.LoginViewModel
import mx.com.satoritech.creditaco.navigation.Screens

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Surface(Modifier.fillMaxSize()) {
        BackgroundGradient()
        LoginContent(navController, viewModel)
    }
}

@Composable
private fun LoginContent(navController: NavController, viewModel: LoginViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoginCard(viewModel, navController)
    }
}

@Composable
private fun LoginCard(
    viewModel: LoginViewModel,
    navController: NavController
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val errorMessages by viewModel.messageError.collectAsStateWithLifecycle()

    Card() {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.login_login_user), style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(40.dp))

            LoginForm(
                errorMessages,
                email,
                password,
                onEmailChanged = {
                    email = it
                    viewModel.setValuesMessageError(ErrorMessage(emailMsg = 0))
                }
            ) {
                password = it
                viewModel.setValuesMessageError(ErrorMessage(passwordConfirmationMsg = 0))
            }

            Spacer(modifier = Modifier.height(35.dp))

            LoginButtons(
                onClickLogin = {
                    viewModel.login(UserLogin(email, password)) {
                        if (it) {
                            navController.navigate(Screens.HOME_SCREEN) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                },
                onClickRegistration = {
                    viewModel.setUserEmail(email)
                    navController.navigate(Screens.REGISTRATION_SCREEN)
                }
            )
        }
    }
}

@Composable
private fun LoginButtons(
    onClickLogin: () -> Unit,
    onClickRegistration: () -> Unit
) {
    CustomButton(
        Modifier.fillMaxWidth(),
        nameButton = stringResource(R.string.login_login_user)
    ) { onClickLogin() }

    Spacer(modifier = Modifier.height(10.dp))

    CustomButton(nameButton = stringResource(R.string.login_register_user), isSecondayButton = true) { onClickRegistration() }
}

@Composable
private fun LoginForm(
    errorMessages: ErrorMessage,
    email: String,
    password: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit
) {

    CustomOutlinedTextField(
        Modifier.fillMaxWidth(),
        value = email,
        onValueChange = { onEmailChanged(it) },
        placeholder = stringResource(R.string.login_add_email),
        label = stringResource(R.string.login_user_email),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        errorMessage = errorMessages.emailMsg
    )

    Spacer(modifier = Modifier.height(20.dp))

    CustomOutlinedTextField(
        Modifier.fillMaxWidth(),
        value = password,
        onValueChange = { onPasswordChanged(it) },
        placeholder = stringResource(R.string.login_add_password),
        label = stringResource(R.string.login_user_password),
        isPasswordTextField = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        errorMessage = errorMessages.passwordMsg
    )
}
