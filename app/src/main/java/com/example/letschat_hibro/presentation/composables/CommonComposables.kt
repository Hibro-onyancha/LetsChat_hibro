package com.example.letschat_hibro.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.letschat_hibro.R
import com.example.letschat_hibro.data.models.Chat
import com.example.letschat_hibro.data.models.Message
import com.example.letschat_hibro.presentation.theme.extraLargeText
import com.example.letschat_hibro.presentation.theme.largeText
import com.example.letschat_hibro.presentation.theme.mediumLargeText
import com.example.letschat_hibro.presentation.theme.mediumText
import com.example.letschat_hibro.presentation.theme.smallText


@Composable
fun CommonText(
    modifier: Modifier = Modifier,
    textSize: Int = 1,
    textAlign: Int = 1,
    color: Color = MaterialTheme.colorScheme.onBackground,
    isBold: Boolean = true,
    text: String,
    maxLines: Int = 1
) {
    Text(
        text = text,
        color = color,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
        fontWeight = if (isBold) FontWeight.Bold else FontWeight.Light,
        fontSize = when (textSize) {
            1 -> smallText
            2 -> mediumText
            3 -> mediumLargeText
            4 -> largeText
            else -> extraLargeText
        },
        textAlign = when (textAlign) {
            1 -> TextAlign.Start
            2 -> TextAlign.Center
            else -> TextAlign.End
        }
    )
}

@Composable
fun InputComp(
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    value: String,
    hint: String = "message"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.97f)
            .padding(top = 20.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            placeholder = {
                Text(
                    text = hint
                )
            },
            shape = RoundedCornerShape(60.dp),
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                errorTextColor = MaterialTheme.colorScheme.error,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            maxLines = 30,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(onDone = { onSend() })
        )
        Spacer(modifier = Modifier.width(5.dp))
        Icon(
            painter = painterResource(id = R.drawable.send),
            contentDescription = "send a message",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .clickable { onSend() }
                .background(if (value == "") MaterialTheme.colorScheme.inverseSurface else MaterialTheme.colorScheme.primaryContainer)
                .padding(7.dp)
        )
    }
}


@Composable
fun ChatComp(
    message: Chat,
    userName: String,
    onUpdate: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(top = 5.dp),
        horizontalArrangement = if (message.userName == userName) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .clip(
                    if (message.userName != userName) RoundedCornerShape(
                        topEnd = 20.dp,
                        topStart = 20.dp,
                        bottomEnd = 20.dp
                    ) else RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp, bottomStart = 20.dp)
                )
                .clickable {
                    onUpdate()
                }
                .background(if (message.userName != userName) MaterialTheme.colorScheme.inverseOnSurface else MaterialTheme.colorScheme.primaryContainer)
                .padding(5.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.97f)
                    .padding(start = 5.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                CommonText(
                    text = message.userName,
                    color = Color.Cyan,
                    textSize = 3
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                CommonText(
                    text = message.message,
                    color = MaterialTheme.colorScheme.onBackground,
                    textSize = 4,
                    maxLines = 2000
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                CommonText(
                    text = message.time,
                    color = MaterialTheme.colorScheme.outline,
                    textSize = 1
                )
            }
        }
    }
}