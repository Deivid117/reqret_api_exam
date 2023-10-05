package mx.com.satoritech.creditaco.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dwh.regres_evaluation.domain.model.response.Users
import com.dwh.regres_evaluation.ui.screens.LoginScreen
import com.dwh.regres_evaluation.ui.screens.home.HomeScreen
import com.dwh.regres_evaluation.ui.screens.registration.RegistrationScreen
import com.dwh.regres_evaluation.ui.screens.user.UserManagementScreen
import mx.com.satoritech.creditaco.ui.`is`.user.logged.WelcomeScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation(navController: NavController) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.WELCOME_SCREEN
    ) {
        // Pantalla inicial
        composable(Screens.WELCOME_SCREEN) {
            WelcomeScreen(navController)
        }

        // Inicio sesión
        composable(Screens.LOGIN_SCREEN) {
            LoginScreen(navController)
        }

        // Registro
        composable(
            Screens.REGISTRATION_SCREEN
        ) {
            RegistrationScreen(navController)

        }

        // Home
        composable(Screens.HOME_SCREEN) {
            HomeScreen(navController)
        }

        // Editar/Crear usuario
        composable(Screens.EDIT_USER + "/{flow}") {
            val user =
                navController.previousBackStackEntry?.savedStateHandle?.get<Users>("user")
            val isUserEdit = it.arguments?.getString("flow") ?: "1"
            UserManagementScreen(navController, user, isUserEdit)
        }
    }
}
