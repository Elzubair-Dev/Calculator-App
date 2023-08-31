package com.qco.calculator.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qco.calculator.main_screen.main.MainScreen
import com.qco.calculator.operation.Action
import com.qco.calculator.splash_screen.SplashScreen

@Composable
fun Navigation(result: String,
               isChecked: Boolean,
               onCheckedChange:()->Unit,
               onAction : (action: Action) -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash_screen" ){
        composable(route = "splash_screen"){
            SplashScreen(navController = navController)
        }
        composable(route = "main_screen"){
            MainScreen(result = result, isChecked = isChecked, navController = navController, onCheckedChange = onCheckedChange, onAction = onAction)
        }
    }
}