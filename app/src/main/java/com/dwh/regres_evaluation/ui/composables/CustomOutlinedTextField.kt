package com.dwh.regres_evaluation.ui.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.dwh.regres_evaluation.R

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    isPasswordTextField: Boolean = false,
    isSearchTextField: Boolean = false,
    errorMessage: Int
) {
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = placeholder,
                color = if(isSystemInDarkTheme()) Color.LightGray else Color.Gray
            )
        },
        singleLine = true,
        label = {
            Text(
                text = label,
            )
        },
        trailingIcon = {
            if(isPasswordTextField) {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(id = if(isPasswordVisible) R.drawable.ic_eye_active else  R.drawable.ic_eye_inactive),
                        contentDescription = "password visible icon",
                    )
                }
            }
            if(isSearchTextField) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = "search icon")
            }
        },
        visualTransformation = if(isPasswordTextField) if (isPasswordVisible) visualTransformation else PasswordVisualTransformation() else visualTransformation,
        keyboardOptions = keyboardOptions
    )
    Spacer(modifier = Modifier.height(5.dp))
    ErrorMessageItem(errorMessage)
}