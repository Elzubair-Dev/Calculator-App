package com.qco.calculator.main_screen.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.qco.calculator.operation.Action
import com.qco.calculator.operation.Operation
import com.qco.calculator.main_screen.ui_components.buttons.TextButton

@Composable
fun CalcRow(operation: Operation, number: Int, onAction : (Action) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextButton(symbol = "$number", textColor = MaterialTheme.colors.onBackground, onClick = {onAction(Action.Number(number))})
        TextButton(symbol = "${number + 1}", textColor = MaterialTheme.colors.onBackground, onClick = {onAction(Action.Number(number + 1))})
        TextButton(symbol = "${number + 2}", textColor = MaterialTheme.colors.onBackground, onClick = {onAction(Action.Number(number + 2))})
        TextButton(symbol = operation.symbol, textColor = MaterialTheme.colors.onBackground, onClick = {onAction(Action.Operations(operation = operation))})
    }
}