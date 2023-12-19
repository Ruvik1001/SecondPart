package com.example.seven.screens.host

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.rememberNavController
import com.example.seven.data.AlertActions
import com.example.seven.data.Screen
import com.example.seven.function.GetAlert
import com.example.seven.function.GetBottomAppBar
import com.example.seven.function.GetDrawer
import com.example.seven.function.GetDrawerContent
import com.example.seven.function.GetScaffold
import com.example.seven.function.GetTopAppBar
import com.example.seven.screens.first.LoadingScreen
import com.example.seven.screens.first.LoadingViewModel
import com.example.seven.screens.second.SeePhotoScreen
import com.example.seven.screens.second.SeePhotoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartHost(
    hostViewModel: HostViewModel,
    loadingViewModel: LoadingViewModel,
    seePhotoViewModel: SeePhotoViewModel,
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = hostViewModel.drawerState.value)
    var currentScreen by remember { mutableStateOf(hostViewModel.currentScreen.value) }
    var alertInfo by remember { mutableStateOf(hostViewModel.alertInfo.value) }
    val listOfButtons = listOf<Triple<ImageVector, String, () -> Unit>>(
        Triple(Icons.Default.Add, "Поиск фото") {currentScreen = Screen.FirstScreen },
        Triple(Icons.Default.Person, "Просмотр") {currentScreen = Screen.SecondScreen }
    )

    LaunchedEffect(drawerState.isClosed) {
        hostViewModel.drawerState.value = drawerState.currentValue
    }

    LaunchedEffect(currentScreen.name) {
        hostViewModel.currentScreen.value = currentScreen
    }

    LaunchedEffect(alertInfo) {
        hostViewModel.alertInfo.value = alertInfo
    }

    LaunchedEffect(hostViewModel.currentScreen.value) {
        currentScreen = hostViewModel.currentScreen.value
    }

    GetDrawer(
        drawerState = drawerState,
        content = { GetDrawerContent(listOfButtons = listOfButtons) }
    ) {
        GetScaffold(
            topAppBar = {
                GetTopAppBar(
                    label = when (currentScreen) {
                        Screen.FirstScreen -> "Поиск фото"
                        Screen.SecondScreen -> "Просмотр"
                        Screen.AlertScreen -> ""
                                                 },

                    action = {
                        when(currentScreen) {
                            Screen.SecondScreen ->
                                IconButton(onClick = {
                                    currentScreen = Screen.AlertScreen
                                    alertInfo = hostViewModel.alertMap[AlertActions.Delete]!!
                                }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                                }
                            else -> {}
                        }
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
                    Screen.FirstScreen -> LoadingScreen(paddingValues, loadingViewModel)
                    Screen.SecondScreen -> SeePhotoScreen(paddingValues, seePhotoViewModel)
                    Screen.AlertScreen -> GetAlert(
                        label = alertInfo.label,
                        text = alertInfo.text,
                        confirmButton = alertInfo.confirmButton,
                        dismissButton = alertInfo.dismissButton,
                        onDismissRequest = alertInfo.onDismissButton
                    )
                }
            }
        )
    }
}