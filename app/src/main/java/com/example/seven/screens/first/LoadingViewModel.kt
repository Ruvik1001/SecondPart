package com.example.seven.screens.first

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoadingViewModel(val context: Context): ViewModel() {
    var url: MutableState<String> = mutableStateOf("http://example.com/image.jpg")
}