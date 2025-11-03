package com.example.trabajokotlin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trabajokotlin.ui.screens.CartScreen
import com.example.trabajokotlin.ui.screens.LoginScreen
import com.example.trabajokotlin.ui.screens.ProductListScreen
import com.example.trabajokotlin.viewmodel.MainViewModel

@Composable
fun AppNavigation(vm: MainViewModel) {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = "login") {
        composable("login") {
            LoginScreen(onLogin = { nav.navigate("products") })
        }
        composable("products") {
            ProductListScreen(
                vm = vm,
                onGoToCart = { nav.navigate("cart") }
            )
        }
        composable("cart") {
            CartScreen(
                vm = vm,
                onBack = { nav.popBackStack() }
            )
        }
    }
}
