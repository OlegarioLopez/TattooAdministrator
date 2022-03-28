package com.ole.tattoadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ole.citastatto.ui.theme.CitasTattoTheme
import com.ole.tattoadmin.ui.Screens.SaveMonthData

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {
            CitasTattoTheme {
                Navigation(this)
            }
        }
    }

}


