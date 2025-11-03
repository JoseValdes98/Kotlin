package com.example.trabajokotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.trabajokotlin.ui.navigation.AppNavigation
import com.example.trabajokotlin.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm = MainViewModel() // ViewModel estático
            MaterialTheme {
                Surface {
                    AppNavigation(vm) // Solo la navegación entre pantallas
                }
            }
        }
    }
}
