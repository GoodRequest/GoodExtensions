package gr.extensions

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.putCursorAtEnd() = setSelection(text.length)

inline fun EditText.onKeyboardDone(crossinline action: () -> Unit) = setOnEditorActionListener { _, keyAction, _ ->
    if(keyAction == EditorInfo.IME_ACTION_DONE) {
        action()
        true
    }
    else false
}