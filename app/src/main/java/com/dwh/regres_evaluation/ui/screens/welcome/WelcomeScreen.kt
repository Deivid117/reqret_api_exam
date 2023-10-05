package mx.com.satoritech.creditaco.ui.`is`.user.logged

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dwh.regres_evaluation.ui.viewmodel.welcome.WelcomeViewModel
import mx.com.satoritech.creditaco.navigation.Screens

@SuppressLint("UnrememberedMutableState")
@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: WelcomeViewModel = hiltViewModel(),
) {
    LaunchedEffect(viewModel) {
        viewModel.isThereLoggedUser {
            if(it) {
                navController.navigate(Screens.HOME_SCREEN) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            } else {
                navController.navigate(Screens.LOGIN_SCREEN) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }
    }
}