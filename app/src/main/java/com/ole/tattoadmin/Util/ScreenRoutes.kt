package com.ole.tattoadmin.Util

sealed class ScreenRoutes(val route: String){
    object MainScreen: ScreenRoutes("main_screen")
    object SusccessScreen: ScreenRoutes("susccess_screen ")
}
