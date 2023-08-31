package com.qco.calculator.main_screen.ui_components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.qco.calculator.ui.theme.Green

@Composable
fun AppBar(isChecked: Boolean, onCheckedChange :()->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(checked = isChecked, onCheckedChange = {onCheckedChange()}, colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colors.onBackground,
            checkedTrackColor = MaterialTheme.colors.onBackground
        ))
    }
}