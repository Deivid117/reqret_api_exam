package com.dwh.regres_evaluation.ui.screens.registration

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dwh.regres_evaluation.R
import com.dwh.regres_evaluation.domain.model.ErrorMessage
import com.dwh.regres_evaluation.domain.model.request.UserRegister
import com.dwh.regres_evaluation.ui.composables.*
import com.dwh.regres_evaluation.ui.viewmodel.registration.RegistrationViewModel
import mx.com.satoritech.creditaco.navigation.Screens

@Composable
fun RegistrationScreen(
    navController: NavController = rememberNavController(),
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getUserEmail()
    }

    CustomScaffold(
        title = stringResource(R.string.registration_topbar_title),
        showReturnIcon = true,
        onBackClick = { navController.popBackStack() }
    ) {
        BackgroundGradient()
        RegistrationContent(navController, viewModel)
    }
}

@Composable
fun RegistrationContent(
    navController: NavController,
    viewModel: RegistrationViewModel,
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RegistrationCard(
            viewModel,
            navController
        )
    }
}

@Composable
private fun RegistrationCard(
    viewModel: RegistrationViewModel,
    navController: NavController
) {
    val userEmail by viewModel.userEmailDataStore.collectAsStateWithLifecycle()
    var email by rememberSaveable { mutableStateOf(userEmail.email) }
    LaunchedEffect(userEmail.email) {
        email = userEmail.email
    }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordConfirmation by rememberSaveable { mutableStateOf("") }
    val errorMessages by viewModel.messageError.collectAsStateWithLifecycle()

    Card() {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(R.string.registration_register), style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.registration_warning_message),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(modifier = Modifier.height(15.dp))

            RegistrationForm(
                errorMessages,
                email,
                password,
                passwordConfirmation,
                onEmailChanged = {
                    email = it
                    viewModel.setValuesMessageError(ErrorMessage(emailMsg = 0))
                },
                onPasswordChanged = {
                    password = it
                    viewModel.setValuesMessageError(ErrorMessage(passwordMsg = 0))
                },
                onPasswordConfirmationChanged = {
                    passwordConfirmation = it
                    viewModel.setValuesMessageError(ErrorMessage(passwordConfirmationMsg = 0))
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            RegistrationButton {
                viewModel.addUser(
                    UserRegister(email, password, passwordConfirmation)
                ) { success ->
                    if (success) navController.navigate(Screens.HOME_SCREEN) {
                        launchSingleTop = true
                    }
                }
            }
        }
    }
}

@Composable
private fun RegistrationButton(
    onClickAction: () -> Unit
) {
    CustomButton(nameButton = stringResource(R.string.registration_register_button)) {
        onClickAction()
    }
}

@Composable
private fun RegistrationForm(
    errorMessages: ErrorMessage,
    email: String,
    password: String,
    passwordConfirmation: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onPasswordConfirmationChanged: (String) -> Unit
) {

    CustomOutlinedTextField(
        Modifier.fillMaxWidth(),
        value = email,
        onValueChange = { onEmailChanged(it) },
        placeholder = stringResource(R.string.registration_email_placeholder),
        label = stringResource(R.string.registration_email),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        errorMessage = errorMessages.emailMsg
    )

    Spacer(modifier = Modifier.height(20.dp))

    CustomOutlinedTextField(
        Modifier.fillMaxWidth(),
        value = password,
        onValueChange = { onPasswordChanged(it) },
        placeholder = stringResource(R.string.registration_password_placeholder),
        label = stringResource(R.string.registration_password),
        isPasswordTextField = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        errorMessage = errorMessages.passwordMsg
    )

    Spacer(modifier = Modifier.height(20.dp))

    CustomOutlinedTextField(
        Modifier.fillMaxWidth(),
        value = passwordConfirmation,
        onValueChange = { onPasswordConfirmationChanged(it) },
        placeholder = stringResource(R.string.registration_password_confirmation_placeholder),
        label = stringResource(R.string.registration_password_cofirmation),
        isPasswordTextField = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        errorMessage = errorMessages.passwordConfirmationMsg
    )

}


