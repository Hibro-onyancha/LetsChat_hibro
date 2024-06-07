package com.example.letschat_hibro.presentation.main_screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.letschat_hibro.data.models.Address
import com.example.letschat_hibro.data.models.Chat
import com.example.letschat_hibro.presentation.composables.ChatComp
import com.example.letschat_hibro.presentation.composables.DialogWindow
import com.example.letschat_hibro.presentation.composables.InputComp

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(
    model: ChatModel = hiltViewModel()
) {
    val uiState = model.uiState.collectAsState().value
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    if (model.getCurrentName(context) != "") {
        DisposableEffect(key1 = lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_START) {
                    model.initiateConnection(context)
                } else if (event == Lifecycle.Event.ON_STOP) {
                    model.disconnect()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (model.getCurrentName(context) == "") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.Center
            ) {
                DialogWindow(
                    isVisible = model.getCurrentName(context) == "",
                    onValueChange = {
                        model.updateUserName(it)
                    },
                    onDismiss = { /*do nothing*/ },
                    onDone = {
                        model.onConnect(context)
                    },
                    value = model.userName
                )
            }
        } else {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        InputComp(
                            onValueChange = { model.updateChat(it) },
                            onSend = { model.sendMessage(context = context) },
                            value = model.chat
                        )

                    }
                }
            ) { _ ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(uiState.allChats) {
                        //all the chats will appear here
                        ChatComp(message = it, userName = model.getCurrentName(context)) {

                            val newChat = Chat(
                                message = "Updated message",
                                time = "Updated time",
                                userName = "Updated userName",
                                address = Address(
                                    city = "New City",
                                    country = "New Country",
                                    street = "Ngong Road"
                                )
                            )
                            model.updateChat(id = it.id,newChat)
                        }
                    }
                }
            }
        }
    }
}