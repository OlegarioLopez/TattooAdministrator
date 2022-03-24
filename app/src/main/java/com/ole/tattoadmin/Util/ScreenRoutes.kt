package com.ole.tattoadmin.Util

sealed class ScreenRoutes(val route: String){
    object Home: ScreenRoutes("home")
    object SusccessScreen: ScreenRoutes("susccess_screen ")
    object SpotViewerScreen: ScreenRoutes("spot_viewer_screen")
    object SpotDetailScreen: ScreenRoutes("spot_detail_screen")
    object SaveMonthData: ScreenRoutes("save_month_data")

}
