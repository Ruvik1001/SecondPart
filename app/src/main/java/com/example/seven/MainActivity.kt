package com.example.seven

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seven.ui.theme.Gray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirstScreen()
        }
    }
}

enum class Screen {
    Downloads, Gallery, Drawer
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen() {
    var url by remember { mutableStateOf("") }
    var screen by remember { mutableStateOf(Screen.Downloads) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        topBar = {
            TopAppBar(
                title = { Text("Загрузки") },
                navigationIcon = {
                    IconButton(onClick = { screen = Screen.Drawer }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    )
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center)
                )
                {
                    IconButton(
                        modifier = Modifier.padding(16.dp),
                        onClick = { screen = Screen.Downloads }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                    IconButton(
                        modifier = Modifier.padding(16.dp),
                        onClick = { screen = Screen.Gallery }) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    }
                }

            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .wrapContentSize(Alignment.Center)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Загрузите ваше фото!",
                fontSize = 24.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .wrapContentSize(Alignment.Center)
            ) {
                Text("URL: ", fontSize = 18.sp)
                BasicTextField(
                    value = url,
                    onValueChange = { url = it },
                    textStyle = TextStyle(fontSize = 18.sp),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth(0.8f)
                        .background(Gray)
                )
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    // Add your logic for downloading and saving the image here
                }
            ) {
                Text("Загрузить")
            }
            Text("ИКБО-06-21", fontSize = 20.sp)
        }
    }
}

