package com.example.seven.screens.first

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seven.ui.theme.Gray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LoadingScreen(
    paddingValues: PaddingValues,
    viewModel: LoadingViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Добро пожаловать!",
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("URL: ", fontSize = 18.sp)
            BasicTextField(
                value = viewModel.url.value,
                onValueChange = { viewModel.url.value = it },
                textStyle = TextStyle(fontSize = 18.sp),

                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
                    .background(Gray)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp),
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    val url = viewModel.url.value

                    try {
                        val image = loadImageFromNetwork(url)
                        saveImageToInternalStorage(image, viewModel.context)

                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                viewModel.context,
                                "Изображение загружено!",
                                Toast.LENGTH_LONG
                            ).show()
                            viewModel.url.value = ""
                        }
                    } catch (e: Exception) {
                        errorMessage = "Ошибка загрузки изображения: ${e.message}"
                        showErrorDialog = true
                    }
                }
            }
        ) {
            Text("Загрузить")
        }

        Spacer(modifier = Modifier.height(16.dp))



//        if (showErrorDialog) {
//        }
    }
}

private fun loadImageFromNetwork(imageUrl: String): Bitmap {
    val url = URL(imageUrl)
    url.openStream().use { input ->
        return Bitmap.createBitmap(BitmapFactory.decodeStream(input))
    }
}

private fun saveImageToInternalStorage(bitmap: Bitmap, context: Context) {
    val timeStamp = SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault()).format(Date())
    val fileName = "image_$timeStamp.jpg"
    val stream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    stream.close()
}