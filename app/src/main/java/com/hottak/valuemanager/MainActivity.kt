package com.hottak.valuemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hottak.valuemanager.ui.screens.HomeScreen
import com.hottak.valuemanager.ui.screens.ReadINIScreen
import com.hottak.valuemanager.ui.theme.ValueManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ValueManagerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "home_screen",
                ) {
                    composable("home_screen") {
                        HomeScreen(navController)
                    }
                    composable("read_ini_screen") {
                        ReadINIScreen()
                    }
                }
            }
        }
    }
}

