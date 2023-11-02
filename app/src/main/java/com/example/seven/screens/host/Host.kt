package com.example.seven.screens.host

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.seven.data.Screen
import com.example.seven.function.GetBottomAppBar
import com.example.seven.function.GetDrawer
import com.example.seven.function.GetDrawerContent
import com.example.seven.function.GetScaffold
import com.example.seven.function.GetTopAppBar
import com.example.seven.screens.first.FirstScreen
import com.example.seven.screens.first.FirstScreenViewModel
import com.example.seven.screens.second.SecondScreen
import com.example.seven.screens.second.SecondScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartHost(
    hostViewModel: HostViewModel,
    firstScreenViewModel: FirstScreenViewModel,
    secondScreenViewModel: SecondScreenViewModel
) {
    val navController = rememberNavController()

    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = hostViewModel.drawerState.value)
    var currentScreen by remember { mutableStateOf(hostViewModel.currentScreen.value) }
    val listOfButtons = listOf<Triple<ImageVector, String, () -> Unit>>(
        Triple(Icons.Default.Add, "Добавить") {currentScreen = Screen.FirstScreen },
        Triple(Icons.Default.Person, "Мои картинки") {currentScreen = Screen.SecondScreen }
    )

    LaunchedEffect(drawerState.isClosed) {
        hostViewModel.drawerState.value = drawerState.currentValue
    }

    LaunchedEffect(currentScreen.name) {
        hostViewModel.currentScreen.value = currentScreen
    }

    GetDrawer(
        drawerState = drawerState,
        content = { GetDrawerContent(listOfButtons = listOfButtons) }
    ) {
        GetScaffold(
            topAppBar = {
                GetTopAppBar(
                    label = when(currentScreen) {
                        Screen.FirstScreen -> "Загрузить"
                        Screen.SecondScreen -> "Галлерея"
                    }
                ) {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            },

            bottomAppBar = {
                GetBottomAppBar(listOfButtons = listOfButtons)
            },

            mainContent = { paddingValues ->
                when (currentScreen) {
                    Screen.FirstScreen -> FirstScreen(paddingValues, firstScreenViewModel)
                    Screen.SecondScreen -> SecondScreen(paddingValues, secondScreenViewModel)
                }
            }
        )
    }
}