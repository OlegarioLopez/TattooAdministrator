package com.ole.tattoadmin.presentation.saveMonthData

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ole.tattoadmin.presentation.home.saveMonthDataViewModel

@Composable
fun dayCheckBox(day: String,number: Int, viewModel: saveMonthDataViewModel){

    val dayCheckboxNumber = rememberSaveable { number}
    val viewModel = remember( ) {
        viewModel
    }
    Spacer(modifier = Modifier.size(12.dp))
    Column(
        Modifier.width(IntrinsicSize.Max)
    ) {
        val isChecked = rememberSaveable() { mutableStateOf(false) }
        Box( modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center){
            Checkbox(
                modifier = Modifier.fillMaxWidth(),
                checked = isChecked.value,
                onCheckedChange = {
                    isChecked.value = it
                    if(isChecked.value) viewModel.addDay(dayCheckboxNumber)
                    else viewModel.deleteDay(dayCheckboxNumber)
                },
                colors = CheckboxDefaults.colors(Color.Green)
            )
        }

        Spacer(modifier = Modifier.size(6.dp))
        Text(day)
    }
}

