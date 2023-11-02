package com.example.seven.screens.host

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.seven.data.Screen

class HostViewModel: ViewModel() {
    @OptIn(ExperimentalMaterial3Api::class)
    var drawerState: MutableState<DrawerValue> = mutableStateOf(DrawerValue.Closed)
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.FirstScreen)
}