package com.dwh.regres_evaluation.ui.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dwh.regres_evaluation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComposable(
    title: String = "",
    showReturnIcon: Boolean,
    showLogoutIcon: Boolean,
    onClickNav: () -> Unit,
    onClickLogout: () -> Unit
) {

    TopAppBar(
        navigationIcon = {
            if(showReturnIcon) {
                IconButton(
                    onClick = { onClickNav() },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back left icon",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(24.dp)
                    )
                } } },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.background
            ) },
        actions = {
            if(showLogoutIcon) {
                IconButton(onClick = { onClickLogout() }) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "logout icon",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
    )
}