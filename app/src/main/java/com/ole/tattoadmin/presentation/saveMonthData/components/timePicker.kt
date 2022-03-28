package com.ole.tattoadmin.presentation.saveMonthData.components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun timePicker(context: Context, value: Int){

    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    val time = remember { mutableStateOf("") }
    val timePickerDialog = TimePickerDialog(
        context,
        {_, hour : Int, minute: Int ->
            time.value = "$hour:$minute"
        }, hour, minute, false
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Selected Time: ${time.value}")
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = {
            timePickerDialog.show()
        }) {
            Text(text = "Open Time Picker")
        }

    }

}