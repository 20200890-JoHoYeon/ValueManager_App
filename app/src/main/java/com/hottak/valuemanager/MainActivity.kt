package com.hottak.valuemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hottak.valuemanager.ui.components.TopBar
import com.hottak.valuemanager.ui.screens.ReadINIScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                topBar = { TopBar() },
                content = { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "home_screen",
                        modifier = Modifier.padding(paddingValues)

                    ) {
                        composable("home_screen") {
                            HomeScreen(navController)
                        }
                        composable("read_ini_screen") {
                            ReadINIScreen()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD)) // 연한 회색 배경
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Value Manager",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Manage your values efficiently",
                fontSize = 16.sp,
                color = Color(0xFF666666),
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Button(
                onClick = {
                    navController.navigate("read_ini_screen")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
            ) {
                Text(text = "Start", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}