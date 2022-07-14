package com.ayizor.afeme.model.response

import com.ayizor.afeme.model.Category

data class CategoryResponse(
    val status: Boolean? = null,
    val message: String? = "",
    val data: ArrayList<Category>? = null
)

