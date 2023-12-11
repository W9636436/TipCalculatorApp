package com.example.tipcalculator



import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val scaleAnimation: Animatable<Float, AnimationVector1D> = remember { Animatable(initialValue = 0f) }

    AnimationSplashContent(
        scaleAnimation = scaleAnimation,
        navController = navController,
        durationMillisAnimation = 1500,
        delayScreen = 3000L
    )

    DesignSplashScreen(
        imagePainter = painterResource(id = R.drawable.tip_cal),
        scaleAnimation = scaleAnimation
    )
}

@Composable
fun AnimationSplashContent(
    scaleAnimation: Animatable<Float, AnimationVector1D>,
    navController: NavController,
    durationMillisAnimation: Int,
    delayScreen: Long
) {
    val authHelper = FirebaseAuth.getInstance()
    val isLoggedIn by remember { derivedStateOf { authHelper.getCurrentUser() != null } }

    LaunchedEffect(key1 = true) {
        scaleAnimation.animateTo(
            targetValue = 0.5F,
            animationSpec = tween(
                durationMillis = durationMillisAnimation,
                easing = {
                    OvershootInterpolator(3F).getInterpolation(it)
                }
            )
        )

        delay(timeMillis = delayScreen)

        if (isLoggedIn) {
            navController.navigate(DestinationScreen.HomeScreenDest.route) {
                popUpTo(route = DestinationScreen.SplashScreenDest.route) {
                    inclusive = true
                }
            }
        } else {

            navController.navigate(route = DestinationScreen.LoginScreenDest.route) {
                popUpTo(route = DestinationScreen.SplashScreenDest.route) {
                    inclusive = true
                }
            }
        }
    }
}

@Composable
fun DesignSplashScreen(
    modifier: Modifier = Modifier,
    imagePainter: Painter,
    scaleAnimation: Animatable<Float, AnimationVector1D>
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(228, 228, 228, 255),
                        Color(227, 218, 230, 255),
                        Color(224, 225, 228, 255),
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = imagePainter,
                contentDescription = " Splash Screen",
                modifier = modifier
                    .size(400.dp)
                    .scale(scale = scaleAnimation.value),
            )

            Text(
                text = "Tip Calculator",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                modifier = modifier.scale(scale = scaleAnimation.value)
            )
        }
    }
}



//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.coroutines.delay
//
//@Composable
//fun SplashScreen(navController: NavHostController) {
////    val authHelper = FirebaseAuth.getInstance()
//    val splashDuration = 2000L // 2 seconds
////    val isLoggedIn by remember { derivedStateOf { authHelper.getCurrentUser() != null } }
//    // Create a splash screen composable that transitions to the main screen
//    var showSplash by remember { mutableStateOf(true) }
//    LaunchedEffect(Unit) {
//        delay(splashDuration)
//        showSplash = false
//    }
//
//    // When showSplash is true, display the splash screen, otherwise navigate to the main screen
//    if (showSplash) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFFF2F3F4))
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
////            Image(
////                painterResource(id =R.drawable.trval_companion_png ) ,
////                contentDescription = null,
////                tint = Color.White,
////                modifier = Modifier.size(120.dp)
////            )
//                Image(
//                    painter = painterResource(id = R.drawable.stmng_png),
//                    contentDescription = "logo",
//
//                    modifier = Modifier.size(220.dp)
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//
//
//                Text(
//                    text = "Student Management",
//                    style = TextStyle(
//                        fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color(
//                            0xFFFF9800
//                        )
//                    ),
//                    modifier = Modifier
//                        .padding(16.dp)
//
////                        .align(Alignment.BottomStart)
//                )
//
//            }
//        }
//
//    } else {
//
////        if (isLoggedIn) {
//            navController.navigate("home_screen")
////        } else {
////            LoginScreen(navController)
//        }
//
//
////    }
//}
