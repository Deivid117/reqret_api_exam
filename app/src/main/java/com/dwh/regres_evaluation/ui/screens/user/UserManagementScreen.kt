package com.dwh.regres_evaluation.ui.screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dwh.regres_evaluation.R
import com.dwh.regres_evaluation.domain.model.ErrorMessage
import com.dwh.regres_evaluation.domain.model.request.CreateUser
import com.dwh.regres_evaluation.domain.model.request.UpdateUser
import com.dwh.regres_evaluation.domain.model.response.Users
import com.dwh.regres_evaluation.ui.composables.CustomButton
import com.dwh.regres_evaluation.ui.composables.CustomDialog
import com.dwh.regres_evaluation.ui.composables.CustomOutlinedTextField
import com.dwh.regres_evaluation.ui.composables.CustomScaffold
import com.dwh.regres_evaluation.ui.viewmodel.logout.LogoutViewModel
import com.dwh.regres_evaluation.ui.viewmodel.user.CreateUserViewModel
import com.dwh.regres_evaluation.ui.viewmodel.user.EditUserViewModel
import com.dwh.regres_evaluation.utils.Constants.EDIT_USER
import mx.com.satoritech.creditaco.navigation.Screens
import kotlin.math.log

@Composable
fun UserManagementScreen(
    navController: NavController,
    user: Users?,
    isUserEdit: String,
    viewModel: EditUserViewModel = hiltViewModel(),
    createUserViewModel: CreateUserViewModel = hiltViewModel(),
) {
    CustomScaffold(
        title = if(isUserEdit == EDIT_USER) stringResource(R.string.management_edit_topbar_title) else stringResource(
                    R.string.management_create_topbar_title),
        showReturnIcon = true,
        onBackClick = { navController.popBackStack() }
    ) {
        UserEditContent(
            navController,
            user,
            isUserEdit,
            viewModel,
            createUserViewModel,
        )
    }
}

@Composable
fun UserEditContent(
    navController: NavController,
    user: Users?,
    isUserEdit: String,
    viewModel: EditUserViewModel,
    createUserViewModel: CreateUserViewModel,
) {
    var name by rememberSaveable { mutableStateOf(
        if(isUserEdit != EDIT_USER) ""
        else "${user?.firstName ?: ""} ${user?.lastName ?: ""}")
    }
    var job by rememberSaveable { mutableStateOf("") }
    val errorMessagesEdit by viewModel.messageError.collectAsStateWithLifecycle()
    val errorMessagesCreate by createUserViewModel.messageError.collectAsStateWithLifecycle()

    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 15.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        UserDataForm(
            if(isUserEdit == EDIT_USER) errorMessagesEdit else errorMessagesCreate,
            name,
            job,
            onNameChanged = {
                name = it
                viewModel.setValuesMessageError(ErrorMessage(nameMsg = 0))
                createUserViewModel.setValuesMessageError(ErrorMessage(nameMsg = 0)) },
            onJobChanged = {
                job = it
                viewModel.setValuesMessageError(ErrorMessage(jobMsg = 0))
                createUserViewModel.setValuesMessageError(ErrorMessage(jobMsg = 0))
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        UserManagementButton(
            isUserEdit,
            onClickEditAction = {
                viewModel.updateUser(
                    user?.id ?: 0,
                    UpdateUser(name, job)
                ) { success ->
                    if (success) {
                        name = ""
                        job = ""
                        navController.popBackStack()
                    }
                }
            },
            onClickCreateAction = {
                createUserViewModel.createUser(CreateUser(name, job)) { success ->
                    if (success) {
                        name = ""
                        job = ""
                        navController.popBackStack()
                    }
                }
            }
        )
    }
}

@Composable
private fun UserManagementButton(
    isUserEdit: String,
    onClickEditAction: () -> Unit,
    onClickCreateAction: () -> Unit
) {
    CustomButton(nameButton = stringResource(R.string.management_save_button)) {
        if (isUserEdit == EDIT_USER) {
            onClickEditAction()
        } else {
            onClickCreateAction()
        }
    }
}

@Composable
private fun UserDataForm(
    errorMessages: ErrorMessage,
    name: String,
    job: String,
    onNameChanged: (String) -> Unit,
    onJobChanged: (String) -> Unit
) {
    CustomOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = name.split(" ").joinToString(" ") { it.capitalize() },
        onValueChange = { onNameChanged(it) },
        placeholder = stringResource(R.string.management_name_placeholder),
        label = stringResource(R.string.management_name),
        errorMessage = errorMessages.nameMsg
    )

    Spacer(modifier = Modifier.height(20.dp))

    CustomOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = job.split(" ").joinToString(" ") { it.capitalize() },
        onValueChange = { onJobChanged(it) },
        placeholder = stringResource(R.string.management_job_placeholder),
        label = stringResource(R.string.management_job),
        errorMessage = errorMessages.jobMsg
    )
}
