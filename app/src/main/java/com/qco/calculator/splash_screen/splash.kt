package com.qco.calculator.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.qco.calculator.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true){
        delay(3000L)
        navController.navigate(route = "main_screen")
    }

    //to give app a fixed layout direction whatever language device use
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(width = 1.dp,
                    color = MaterialTheme.colors.onBackground,
                    shape = CircleShape)
                .padding(20.dp)) {
                //App Icon
                Image(painter = painterResource(id = R.drawable.calculator),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                    modifier = Modifier
                        .align(alignment = Alignment.Center))
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //Developer
                Text(text = "Developed By: Elzubair Elbushra", color = MaterialTheme.colors.onBackground, fontSize = 9.sp, fontWeight = FontWeight.Thin)

                //Sudan flag
                Image(painter = painterResource(id = R.drawable.sudan),
                    contentDescription = "Sudanese flag",
                    modifier = Modifier.size(100.dp))
            }

        }
    }
}