package com.example.seven

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.seven.data.Notification
import com.example.seven.screens.first.LoadingViewModel
import com.example.seven.screens.first.LoadingViewModelFactory
import com.example.seven.screens.host.HostViewModel
import com.example.seven.screens.host.HostViewModelFactory
import com.example.seven.screens.host.InitAndShow
import com.example.seven.screens.second.SeePhotoViewModel
import com.example.seven.screens.second.SeePhotoViewModelFactory
import com.example.seven.ui.theme.MyTheme
import com.example.seven.ui.theme.SevenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hostViewModel = ViewModelProvider(this, HostViewModelFactory(this))
            .get(HostViewModel::class.java)

        val loadingViewModel = ViewModelProvider(this, LoadingViewModelFactory(this))
            .get(LoadingViewModel::class.java)

        val seePhotoViewModel = ViewModelProvider(this, SeePhotoViewModelFactory(this))
            .get(SeePhotoViewModel::class.java)

        Notification.schedule(this@MainActivity.applicationContext)

        setContent {
            SevenTheme {
                InitAndShow(
                    hostViewModel,
                    loadingViewModel,
                    seePhotoViewModel
                )
            }
        }
    }
}



