package com.example.tipcalculator

const val ID_KEY = "ID_key"
const val TEXT_KEY = "TEXT_key"
sealed class DestinationScreen(val route: String) {
    object SplashScreenDest : DestinationScreen(route = "splash_screen")

    object LoginScreenDest : DestinationScreen(route = "login_screen")
    object SignupScreenDest : DestinationScreen(route = "signup_screen")
    object HomeScreenDest : DestinationScreen(route = "home_screen")

}