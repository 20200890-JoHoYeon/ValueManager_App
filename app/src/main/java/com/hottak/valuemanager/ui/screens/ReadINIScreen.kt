package com.hottak.valuemanager.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun ReadINIScreen() {
    val context = LocalContext.current
    Scaffold(
        containerColor = Color(0xFFF5F5F5), // 연한 회색 배경
        modifier = Modifier.fillMaxSize(),
        bottomBar = {},
        content = { innerPadding ->
            ReadFileContent(
                context = context,
                innerPadding = innerPadding,
            )
        }
    )
}

@Composable
fun ReadFileContent(innerPadding: PaddingValues, context: Context) {
    val iniData = readSettingIni(context, "sample.ini")
    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(16.dp)
    ) {
        iniData.forEach { (section, values) ->
            item {
                SectionHeader(section)
            }
            items(values.toList()) { (key, value) ->
                KeyValueItem(key, value)
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFF2196F3), shape = MaterialTheme.shapes.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = " $title ",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun KeyValueItem(key: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$key:",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color(0xFF333333),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value,
                fontSize = 16.sp,
                color = Color(0xFF666666),
                modifier = Modifier.weight(2f)
            )
        }
    }
}

fun readSettingIni(context: Context, fileName: String): Map<String, Map<String, String>> {
    val iniData = mutableMapOf<String, MutableMap<String, String>>()
    try {
        val inputStream = context.assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        var currentSection: String? = null

        while (reader.readLine().also { line = it } != null) {
            line = line?.trim()

            if (line.isNullOrEmpty() || line.startsWith("#")) {
                continue // 빈 줄 또는 주석 무시
            }

            if (line.startsWith("[") && line.endsWith("]")) {
                currentSection = line.substring(1, line.length - 1)
                iniData[currentSection] = mutableMapOf()
            } else if (currentSection != null && line.contains("=")) {
                val (key, value) = line.split("=", limit = 2).map { it.trim() }
                iniData[currentSection]?.put(key, value)
            }
        }
        reader.close()
        inputStream.close()
    } catch (e: Exception) {
        Log.e("INI_Read_Error", "Error reading the INI file", e)
    }
    return iniData
}