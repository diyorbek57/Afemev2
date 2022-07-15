package com.ayizor.afeme.helper

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View


public open class CustomSpannable(b: Boolean) : ClickableSpan() {
    private var isUnderline = false

    fun MySpannable(isUnderline: Boolean) {
        this.isUnderline = isUnderline
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = isUnderline
        ds.color = Color.parseColor("#2972FE")
    }

    override fun onClick(widget: View) {

    }

}