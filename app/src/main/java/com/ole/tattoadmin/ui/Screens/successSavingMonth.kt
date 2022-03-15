package com.ole.tattoadmin.ui.Screens

import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun Success() {
    Card() {
        Text(text = "Éxito en el guardado en firestore")
    }
}