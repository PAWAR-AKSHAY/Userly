package com.example.selfinfo.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onTextChange(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged(editable?.toString() ?: "")
        }
    })
}