package com.dwh.regres_evaluation.ui.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    nameButton: String,
    isSecondayButton: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if(!isSecondayButton) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(15.dp),
    ) {
        Text(
            text = nameButton,
        )
    }
}
