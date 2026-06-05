package ec.edu.epn.examen01

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import ec.edu.epn.examen01.presentation.screen.MainScreen
import ec.edu.epn.examen01.presentation.viewmodel.UserViewModel

@Composable
fun App() {

    MaterialTheme {

        val viewModel =
            remember {
                UserViewModel()
            }

        MainScreen(viewModel)
    }
}