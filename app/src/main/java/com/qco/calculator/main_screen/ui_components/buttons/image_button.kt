package com.qco.calculator.main_screen.ui_components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ImageButton(image: Int,
                color: Color = MaterialTheme.colors.onBackground,
                backgroundColor: Color = MaterialTheme.colors.background,
                shape: Shape = RoundedCornerShape(10.dp),
                onClick :()->Unit) {
    Box(modifier = Modifier
        .padding(2.dp)
        .size(60.dp)
        .shadow(elevation = 3.dp, shape = shape)
        .background(color = backgroundColor)
        .clickable { onClick() },
        contentAlignment = Alignment.Center) {
        Image(painter = painterResource(id = image), contentDescription = "", colorFilter = ColorFilter.tint(color = color))
    }
}