package com.nfirex.impl.ui.widgets

import android.text.Editable
import android.text.TextWatcher

class QueryListener(
    private val block: (String) -> Unit
) : TextWatcher {
    override fun afterTextChanged(s: Editable?) = Unit

    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) = Unit

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
        val text = s?.toString() ?: ""
        block(text)
    }
}