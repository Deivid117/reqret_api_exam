package com.dwh.regres_evaluation.ui.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun CustomFloatingActionButton(
    onButtonClick: () -> Unit
) {
    FloatingActionButton(
        onClick = {
            onButtonClick()
        },
        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        shape = RoundedCornerShape(15.dp),
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "user add",
            tint =  MaterialTheme.colorScheme.background,
        )
    }
}