package com.ole.tattoadmin.presentation.saveMonthData

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ole.tattoadmin.presentation.home.saveMonthDataViewModel


@Composable
fun shiftsCheck(viewModel: saveMonthDataViewModel) {
    var shifts = listOf(1,2,3,4)

    Spacer(modifier = Modifier.size(12.dp))
    LazyRow(){
        items(
            items = shifts,
            itemContent = { currentShift ->
                Row(
                ) {
                    Text(text = currentShift.toString())

                    val isChecked = rememberSaveable() { mutableStateOf(false) }

                    Checkbox(
                        modifier = Modifier.fillMaxWidth(),
                        checked = isChecked.value,
                        enabled = if( !isChecked.value && viewModel.someShiftSelected.value) false else true,
                        onCheckedChange = {
                            Log.d("check",viewModel.someShiftSelected.value.toString())
                            viewModel.selectNumberShifts()
                            isChecked.value = it
                        },
                        colors = CheckboxDefaults.colors(Color.Green)
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                }
            })
    }
}

