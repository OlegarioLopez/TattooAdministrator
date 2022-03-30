package com.ole.tattoadmin.presentation.saveMonthData.components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ole.tattoadmin.presentation.home.saveMonthDataViewModel
import java.util.*

@Composable
fun timePicker(context: Context, viewModel: saveMonthDataViewModel){


    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    var shiftNumber = rememberSaveable { mutableStateOf(-1)}
    val time = rememberSaveable { mutableStateOf("") }
    val timePickerDialog = TimePickerDialog(
        context,
        {_, hour : Int, minute: Int ->
            if(shiftNumber.value == -1) shiftNumber.value = viewModel.shiftsTimeFilled.value
            if(viewModel.shiftsTimeFilled.value < 5 && time.value==""  ) viewModel.addShift()
            viewModel.fillShiftTime(shiftNumber.value,hour,minute)
            time.value = "$hour:$minute"
            println(viewModel.listOfShifts.value)

        }, hour, minute, false
    )


    Row(
    ) {
        Spacer(modifier = Modifier.size(width = 8.dp, height = 0.dp))

        Text(text = "${time.value}",
            color = Color.Blue,
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.size(width = 8.dp, height = 0.dp))
        Button(onClick = {
            timePickerDialog.show()
        }) {
            Text(text = "Seleccionar")
        }

    }
}