package com.mediatama.appsatipadang.user.ui.profile

import android.graphics.Paint
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class CustomTypefaceSpan(private val typeface: String) : MetricAffectingSpan() {
    override fun updateMeasureState(p: TextPaint) {
        p.flags or Paint.SUBPIXEL_TEXT_FLAG
    }

    override fun updateDrawState(p: TextPaint) {
        p.flags or Paint.SUBPIXEL_TEXT_FLAG
    }
}
