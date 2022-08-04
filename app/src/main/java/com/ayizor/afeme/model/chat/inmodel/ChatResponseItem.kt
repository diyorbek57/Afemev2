package com.ayizor.afeme.model.chat.inmodel

data class ChatResponseItem(
    val chat_id: String,
    val created: String,
    val created_at: String,
    val `file`: Any,
    val id: Int,
    val message: String,
    val parentID: Any,
    val to: String,
    val updated_at: String
)