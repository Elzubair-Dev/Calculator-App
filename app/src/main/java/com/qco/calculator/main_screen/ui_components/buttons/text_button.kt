package com.qco.calculator.main_screen.ui_components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextButton(symbol:String,
               textColor: Color = MaterialTheme.colors.onBackground,
               backgroundColor: Color = MaterialTheme.colors.background,
               shape: Shape = RoundedCornerShape(10.dp),
               weight: FontWeight = FontWeight.Thin,
               size: Int = 15,
               onClick :()->Unit) {
    Box(modifier = Modifier
        .padding(2.dp)
        .size(60.dp)
        .shadow(elevation = 3.dp, shape = shape)
        .background(color = backgroundColor)
        .clickable { onClick() },
        contentAlignment = Alignment.Center) {
        Text(text = symbol, fontSize = size.sp, fontWeight = weight, color = textColor)
    }
}