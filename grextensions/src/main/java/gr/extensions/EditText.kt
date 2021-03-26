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

inline fun EditText.onKeyboardNext(crossinline action: () -> Unit) = setOnEditorActionListener { _, keyAction, _ ->
    if(keyAction == EditorInfo.IME_ACTION_NEXT) {
        action()
        true
    }
    else false
}

inline fun EditText.onKeyboard(vararg actionType: Int, crossinline action: () -> Unit) = setOnEditorActionListener { _, keyAction, _ ->
    if(keyAction in actionType) {
        action()
        true
    }
    else false
}

fun EditText.onKeyboardAction(action: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE ||
            actionId == EditorInfo.IME_ACTION_SEND ||
            actionId == EditorInfo.IME_ACTION_SEARCH ||
            actionId == EditorInfo.IME_ACTION_GO) {
            action()
        }

        return@setOnEditorActionListener false
    }
}