package com.ole.tattoadmin.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ole.tattoadmin.viewmodels.SpotsViewerViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpotsViewer(navController: NavHostController) {


    var viewModel = SpotsViewerViewModel()
    val listDays = viewModel.daysAvailables
    val listSpots = viewModel.spotsAvailables


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color(0xFF0C120C))
    ) {
        items(
            items = listDays.value,
            itemContent = { currentDay ->
                Text(
                    text = currentDay.weekDay + "," + currentDay.dayInMonth,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(25.dp),
                    textAlign = TextAlign.Center
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color(0xFF0C120C))
                ) {
                    items(
                        items = listSpots.value.filter { it.dayInMonth == currentDay.dayInMonth },
                        itemContent = { currentSpot ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(Alignment.CenterVertically)
                                        .padding(6.dp)
                                        .size(width = 150.dp, height = 100.dp),
                                    onClick = {},
                                    backgroundColor = Color(if (currentSpot.availability) 0xFF3C822C else 0xFFC20114),
                                    elevation = 8.dp,
                                    shape = RoundedCornerShape(16.dp)

                                ) {
                                    Column() {
                                        Text(
                                            text = currentSpot.toStringShort(),
                                            fontSize = 15.sp,
                                            fontFamily = FontFamily.Default,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(vertical = 5.dp).fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )

                                        Text(
                                            text = if (currentSpot.availability) "Disponible" else currentSpot.bookedBy,
                                            fontSize = 15.sp,
                                            fontFamily = FontFamily.Default,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(vertical = 5.dp).fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )

                                    }

                                }

                        })
                }

            })
    }

}





