package com.dwh.regres_evaluation.ui.screens.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dwh.regres_evaluation.R
import com.dwh.regres_evaluation.domain.model.response.Users
import com.dwh.regres_evaluation.ui.composables.*
import com.dwh.regres_evaluation.ui.viewmodel.home.HomeUiState
import com.dwh.regres_evaluation.ui.viewmodel.home.HomeViewModel
import com.dwh.regres_evaluation.ui.viewmodel.home.UserUiState
import com.dwh.regres_evaluation.ui.viewmodel.logout.LogoutViewModel
import com.dwh.regres_evaluation.utils.Constants.CREATE_USER
import com.dwh.regres_evaluation.utils.Constants.EDIT_USER
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import mx.com.satoritech.creditaco.navigation.Screens

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    viewModel: HomeViewModel = hiltViewModel(),
    logoutViewModel: LogoutViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getUsers()
        viewModel.getUserInfo()
    }

    UsersValidationResponse(navController, viewModel, logoutViewModel)
}

@Composable
fun UsersValidationResponse(
    navController: NavController,
    viewModel: HomeViewModel,
    logoutViewModel: LogoutViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userUiState by viewModel.userUiState.collectAsStateWithLifecycle()
    var userEmail by remember { mutableStateOf("N/A") }
    var data = listOf<Users>()

    when(userUiState) {
        is UserUiState.Success -> {
            userEmail = (userUiState as UserUiState.Success).data.email
        }
        is UserUiState.Error -> {
            val errorMessage = (userUiState as UserUiState.Error).errorMessage
            Log.e("UserError", errorMessage)
        }
    }

    when(uiState) {
        is HomeUiState.Error -> {
            val erroMsg = (uiState as HomeUiState.Error).errorMessage
            Log.e("HomeScreenError", erroMsg)
            HomeScaffold(userEmail, navController, logoutViewModel) {
                EmptyData(
                    title = stringResource(R.string.home_empty_message_title),
                    description = stringResource(R.string.home_empty_message_description)
                )
            }
        }
        HomeUiState.Loading -> {
            HomeScaffold(userEmail, navController, logoutViewModel) {
                HomeContent(navController, data, viewModel)
                CustomCircularProgress()
            }
        }
        is HomeUiState.Success -> {
            data = (uiState as HomeUiState.Success).data
            HomeScaffold(userEmail, navController, logoutViewModel) {
                HomeContent(navController, data, viewModel)
            }
        }
    }
}

@Composable
private fun HomeScaffold(
    userEmail: String,
    navController: NavController,
    logoutViewModel: LogoutViewModel,
    content: @Composable () -> Unit
) {
    CustomScaffold(
        title = userEmail,
        showFloatingButton = true,
        showLogoutButton = true,
        onButtonClick = { navController.navigate(Screens.EDIT_USER + "/$CREATE_USER") },
        onClickLogout = {
            logoutViewModel.logout { success ->
                if (success) {
                    navController.navigate(Screens.LOGIN_SCREEN) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    ) {
        content()
    }
}


@Composable
private fun HomeContent(
    navController: NavController,
    users: List<Users>,
    viewModel: HomeViewModel
) {
    var searchText by remember{ mutableStateOf("") }

    SearchUsers(searchText, onSearchChanged = { searchText = it } )

    if(users.isNotEmpty()) {
        val filteredUsersList = FilteredUsersList(searchText = searchText, users).toMutableStateList()
        UsersList(filteredUsersList, navController, viewModel)
    } else {
        EmptyData(
            title = stringResource(R.string.home_empty_message_title),
            description = stringResource(R.string.home_empty_message_description)
        )
    }
}

@Composable
private fun SearchUsers(
    searchText: String,
    onSearchChanged: (String) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp), verticalArrangement = Arrangement.Top) {
        CustomOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchText,
            onValueChange = {  onSearchChanged(it) },
            placeholder = stringResource(R.string.home_search_user_placeholder),
            label = stringResource(R.string.home_search),
            isSearchTextField = true,
            errorMessage = 0
        )
    }
}

@Composable
private fun FilteredUsersList(searchText: String, users: List<Users>, ): List<Users> {
    val filteredList = if (searchText.isEmpty()) {
        users
    } else {
        users.filter {
            it.email?.contains(searchText, ignoreCase = true) == true    ||
            it.lastName?.contains(searchText, ignoreCase = true) == true ||
            it.firstName?.contains(searchText, ignoreCase = true) == true
        }
    }
    return filteredList
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UsersList(
    users: SnapshotStateList<Users>,
    navController: NavController,
    viewModel: HomeViewModel
) {
    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            viewModel.getUsers()
            refreshing = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { refreshing = true },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 90.dp),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            val usersGrouped =
                users.sortedBy { it.firstName }.groupBy { it.firstName?.get(0) ?: "" }
            usersGrouped.forEach { (header, items) ->
                stickyHeader {
                    Text(
                        text = header.toString(),
                        color = Color.Black,
                        modifier = Modifier
                            .background(Color.LightGray)
                            .padding(15.dp)
                            .fillMaxWidth()
                    )
                }
                items(items) {
                    UserItem(it, navController, viewModel, removeItem = { users.remove(it) })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItem(
    user: Users,
    navController: NavController,
    viewModel: HomeViewModel,
    removeItem: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    ShowDeleteDialog(
        showDialog,
        onAccept = {
            viewModel.deleteUser(user.id) {success ->
                if(success) {
                    removeItem()
                    showDialog = false
                }
            }
        },
        onCancel = { showDialog = false }
    )

    val dismissState = rememberDismissState(
        initialValue = DismissValue.Default,
        confirmValueChange = {
            when (it) {
                DismissValue.DismissedToStart -> {
                    navController.currentBackStackEntry?.savedStateHandle?.set("user", user)
                    navController.navigate(Screens.EDIT_USER + "/$EDIT_USER")
                    false
                }
                DismissValue.DismissedToEnd -> {
                    showDialog = true
                    false
                }
                else -> true
            }
        },
        positionalThreshold = { 150.dp.toPx() }
    )

    SwipeToDismiss(
        state = dismissState,
        background = {
            DismissBackground(dismissState) },
        directions = setOf(
            DismissDirection.StartToEnd,
            DismissDirection.EndToStart),
        dismissContent = {
            UserCardInformation(user) },
    )
}

@Composable
private fun UserCardInformation(user: Users) {
    Card(
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(85.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray, CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatar ?: "")
                    .build(),
                contentDescription = "user_image_profile",
                placeholder = painterResource(id = R.drawable.ic_user),
                error = painterResource(id = R.drawable.image_unavailable),
                contentScale = ContentScale.Crop,
            )
            Column {
                Text(text = "${user.firstName ?: "N/A"} ${user.lastName ?: "N/A"}")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = user.email ?: "N/A")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    val color = when (dismissState.dismissDirection) {
        DismissDirection.StartToEnd -> Color(0xFFDB0000)
        DismissDirection.EndToStart -> Color(0xFF01A524)
        null -> Color.Transparent
    }
    val direction = dismissState.dismissDirection

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (direction == DismissDirection.StartToEnd) Icon(
            Icons.Default.Delete,
            contentDescription = "delete_user_icon"
        )
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart) Icon(
            Icons.Default.Edit,
            contentDescription = "edit_user_icon"
        )
    }
}

@Composable
fun ShowDeleteDialog(showDialog: Boolean, onAccept: () -> Unit, onCancel: () -> Unit) {
    if(showDialog) {
        CustomDialog(
            onDissmiss = { onAccept() },
            title = stringResource(R.string.home_dialog_title),
            body = stringResource(R.string.home_dialog_body)
        ) {
            Row(Modifier) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CustomButton(
                        modifier = Modifier.wrapContentWidth(),
                        onClick = { onAccept() }, nameButton = stringResource(R.string.home_yes_button)
                    )
                    CustomButton(
                        modifier = Modifier.wrapContentWidth(),
                        onClick = { onCancel() }, nameButton = stringResource(R.string.home_no_button),
                        isSecondayButton = true
                    )
                }
            }
        }
    }
}
