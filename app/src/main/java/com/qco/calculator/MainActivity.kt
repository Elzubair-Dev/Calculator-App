package com.qco.calculator

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qco.calculator.navigation.Navigation
import com.qco.calculator.operation.ViewModel
import com.qco.calculator.ui.theme.CalculatorTheme

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // state that represent switch button's state
            var isChecked by remember {
                mutableStateOf(false)
            }
            // function to toggle switch button
            fun onCheckedChange(){
                isChecked = !isChecked
            }

            // dark theme value depends on the value of switch button
            CalculatorTheme(darkTheme = isChecked) {

                // to convert status bar text color to dark color
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.also { window.decorView.systemUiVisibility = it }

                // instance of view model class and state
                val viewModel = viewModel<ViewModel>()
                val state = viewModel.state

                // 1st layout from it we can navigate between screens
                Navigation(result = state.result,
                    isChecked = isChecked,
                    onCheckedChange = ::onCheckedChange,
                    onAction = viewModel::onAction)

            }
        }
    }
}

