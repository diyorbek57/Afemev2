package com.ayizor.afeme.model.chat

data class Message(
    val chat: Chat,
    val latest: Latest,
    val user: User
)