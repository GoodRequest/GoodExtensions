package gr.extensions

import android.graphics.Paint
import android.graphics.Typeface
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

var TextView.string: String
    get() = text.toString()
    set(msg) { text = msg }

var TextView.nullString: String?
    get() = text.toString()
    set(msg) { text = msg }

fun TextView.isEmpty(): Boolean = this.text.isEmpty()
infix fun TextView.setTextIfEmpty(text: String) { if(isEmpty()) this.text = text }
infix fun TextView.setTextOrGone(text: String) { goneIf(text.isBlank()); this.text = text }

fun TextView.setTextOrInvisible(text: CharSequence?)                                     { invisibleIf(text?.isBlank() != false || text == "null"); this.text = text }
fun TextView.setTextOrGoneIf(text: CharSequence, block: CharSequence.() -> Boolean)      { goneIf(block(text)); this.text = text }
fun TextView.setTextOrInvisibleIf(text: CharSequence, block: CharSequence.() -> Boolean) { invisibleIf(block(text)); this.text = text }


fun TextView.onTextChange(action: (String) -> Unit) =
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(text: Editable) = action(text.toString())
    })

fun TextView.strikethrough() { paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG }
fun TextView.underline() { paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG }
fun TextView.bold() = setTypeface(null, Typeface.BOLD)
fun TextView.normal() = setTypeface(null, Typeface.NORMAL)