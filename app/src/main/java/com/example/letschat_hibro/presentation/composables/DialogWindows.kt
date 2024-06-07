package com.example.letschat_hibro.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.letschat_hibro.presentation.theme.mediumText
import com.example.letschat_hibro.presentation.theme.smallShape

@Composable
fun DialogWindow(
    isVisible: Boolean,
    onValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onDone: () -> Unit,
    value: String,
    title: String = "Welcome to lets chat project",
    hint: String = "first name",

    ) {
    AnimatedVisibility(visible = isVisible) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = true)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                CommonText(
                    text = title,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = 2,
                    textSize = 4,
                    color = MaterialTheme.colorScheme.primary,
                    isBold = false
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = value,
                    onValueChange = { onValueChange(it) },
                    placeholder = {
                        Text(
                            text = hint
                        )
                    },
                    shape = smallShape,
                    modifier = Modifier.fillMaxWidth(0.7f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        errorTextColor = MaterialTheme.colorScheme.error,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Go
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "chat",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clip(smallShape)
                            .padding(3.dp)
                            .clickable {
                                onDone()
                            },
                        fontWeight = FontWeight.Light,
                        fontSize = mediumText
                    )
                }
            }
        }

    }
}
