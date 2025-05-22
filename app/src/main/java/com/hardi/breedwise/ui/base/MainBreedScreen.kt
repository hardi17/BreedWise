package com.hardi.breedwise.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hardi.breedwise.ui.theme.BreedWiseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainBreedScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // enableEdgeToEdge()
        setContent {
            BreedWiseTheme {
                AppNavigationBar()
            }
        }
    }
}
