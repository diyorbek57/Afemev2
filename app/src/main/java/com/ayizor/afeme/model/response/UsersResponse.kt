package com.ayizor.afeme.model.response

import com.ayizor.afeme.model.user.User

data class UsersResponse (
        val status: Boolean? = null,
        val message: String? = "",
        val data: ArrayList<User>? = null
        )