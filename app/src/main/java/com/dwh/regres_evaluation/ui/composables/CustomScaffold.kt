package com.dwh.regres_evaluation.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomScaffold(
    title: String = "",
    showReturnIcon: Boolean = false,
    showFloatingButton: Boolean = false,
    showLogoutButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onButtonClick: () -> Unit = {},
    onClickLogout: () -> Unit = {},
    content: @Composable() () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBarComposable(
                title = title,
                showReturnIcon = showReturnIcon,
                showLogoutIcon = showLogoutButton,
                onClickNav = { onBackClick() },
                onClickLogout = { onClickLogout() },
            )
        },
        floatingActionButton = {
            if(showFloatingButton) CustomFloatingActionButton { onButtonClick() }
        },
        content = {
                innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }
    )
}