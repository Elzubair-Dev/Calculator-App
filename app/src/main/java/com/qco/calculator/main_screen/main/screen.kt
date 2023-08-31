package com.qco.calculator.main_screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.qco.calculator.R
import com.qco.calculator.operation.Action
import com.qco.calculator.operation.Operation
import com.qco.calculator.main_screen.ui_components.AppBar
import com.qco.calculator.main_screen.ui_components.CalcRow
import com.qco.calculator.main_screen.ui_components.TV
import com.qco.calculator.main_screen.ui_components.buttons.ImageButton
import com.qco.calculator.main_screen.ui_components.buttons.TextButton

@Composable
fun MainScreen(
    result: String,
    isChecked: Boolean,
    navController: NavController,
    onCheckedChange : () -> Unit,
    onAction : (Action) -> Unit
) {
    navController.enableOnBackPressed(enabled = false)

    //to give app a fixed layout direction whatever language device use
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Scaffold(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
            topBar = {
                AppBar(
                    isChecked = isChecked,
                    onCheckedChange = onCheckedChange
                )
            }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                TV(text = result)
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextButton(symbol = "C", weight = FontWeight.Bold, onClick = {onAction(Action.Clear)})
                    TextButton(symbol = "log", onClick = {onAction(Action.Operations(operation =Operation.Log))})
                    TextButton(symbol = "(", onClick = {onAction(Action.Operations(operation =Operation.Open))})
                    TextButton(symbol = ")", onClick = {onAction(Action.Operations(operation =Operation.Close))})
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextButton(symbol = "^", onClick = {onAction(Action.Operations(operation = Operation.Pow))})
                    TextButton(symbol = "!", onClick = {onAction(Action.Operations(operation =Operation.Factorial))})
                    TextButton(symbol = "%", onClick = {onAction(Action.Operations(operation = Operation.Mod))})
                    TextButton(symbol = "/", onClick = {onAction(Action.Operations(operation = Operation.Divide))})
                }

                CalcRow(operation = Operation.Multi, number = 7, onAction = onAction)
                CalcRow(operation = Operation.Sub, number = 4, onAction = onAction)
                CalcRow(operation = Operation.Add, number = 1, onAction = onAction)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextButton(symbol = "0", onClick = {onAction(Action.Number(0))})
                    TextButton(symbol = ".", onClick = {onAction(Action.Point)})
                    ImageButton(image = R.drawable.ic_outline_backspace_24, onClick = {onAction(Action.Delete)})
                    TextButton(symbol = "=", size = 20, weight = FontWeight.Bold, onClick = {onAction(Action.Calculate)})
                }
            }
        }
    }
}