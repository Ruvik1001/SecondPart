package com.example.seven.screens.third

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        Text(
            text = "ИКБО-06-21",
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Руднев В.В.",
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}