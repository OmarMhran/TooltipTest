package com.example.bottomnavigationtest.presetation.ui

import BottomNavigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.bottomnavigationtest.ui.theme.BottomNavigationTestTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BottomNavigationTestTheme {
                BottomNavigation()
            }
        }
    }
}
