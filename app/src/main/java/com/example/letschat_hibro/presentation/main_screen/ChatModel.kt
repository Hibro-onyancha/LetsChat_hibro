package com.example.letschat_hibro.presentation.main_screen

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.letschat_hibro.data.models.Chat
import com.example.letschat_hibro.data.models.UpdateChatRequest
import com.example.letschat_hibro.data.repos.ChatRepo
import com.example.letschat_hibro.data.repos.SocketRepo
import com.example.letschat_hibro.presentation.composables.USER_NAME
import com.example.letschat_hibro.utils.PreferenceManager
import com.example.letschat_hibro.utils.Resource
import com.example.letschat_hibro.utils.getAddressList
import com.example.letschat_hibro.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatModel @Inject constructor(
    private val chatRepo: ChatRepo, private val socketRepo: SocketRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatState())
    val uiState = _uiState.asStateFlow()

    var userName by mutableStateOf("")
        private set
    var chat by mutableStateOf("")
        private set

    fun updateUserName(value: String) {
        userName = value
    }

    fun updateChat(value: String) {
        chat = value
    }


    fun onConnect(context: Context) {
        if (userName != "") {
            _uiState.update {
                it.copy(
                    userName = userName
                )
            }
            val prefs = PreferenceManager(context)
            prefs.saveStringValue(key = USER_NAME, value = userName)
            initiateConnection(context)
        } else {
            Toast.makeText(context, "name input is empty", Toast.LENGTH_SHORT).show()
        }
    }

    fun isTheUserIn(context: Context): Boolean {
        val prefs = PreferenceManager(context)
        val userName = prefs.getStringValue(key = USER_NAME)
        return userName != ""
    }

    fun getCurrentName(context: Context): String {
        val prefs = PreferenceManager(context)
        return prefs.getStringValue(key = USER_NAME)
    }

    fun initiateConnection(context: Context) {
        if (getCurrentName(context) != "") {
            getAllMessages()
            viewModelScope.launch {

                viewModelScope.launch {
                    when (val result = socketRepo.initiateSession(getCurrentName(context))) {
                        is Resource.Success -> {
                            socketRepo.observeChat()
                                .onEach { message ->
                                    val newList = _uiState.value.allChats.toMutableList().apply {
                                        add(0, message)
                                    }
                                    _uiState.update {
                                        it.copy(
                                            allChats = newList
                                        )
                                    }
                                }.launchIn(viewModelScope)
                        }

                        is Resource.Error -> {
                            Log.e("init-connect", "${result.message}")
                        }

                        else -> {
                            /*do nothing*/
                        }
                    }
                }
            }
        }
    }

    private fun getAllMessages() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val allMessages = chatRepo.getAllChats()
            _uiState.update {
                it.copy(
                    allChats = allMessages,
                    isLoading = false

                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(context: Context) {
        viewModelScope.launch {
            val message = Chat(
                userName = getCurrentName(context),
                message = chat,
                time = getCurrentTime(),
                addressList = getAddressList()

            )
            socketRepo.sendChat(message)
            delay(200)
            chat = ""
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            socketRepo.closeSession()
        }
    }

    fun updateChat(id:String,chat: Chat) {
        viewModelScope.launch {
            chatRepo.updateChat(UpdateChatRequest(id = id, newChat = chat))
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}