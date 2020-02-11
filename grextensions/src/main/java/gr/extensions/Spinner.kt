package gr.extensions

import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Spinner

inline fun <reified T : Any> Spinner.getItem(): T? {
    val item = selectedItem
    return when (item) {
        is T -> item
        else -> null
    }
}

@Suppress("UNCHECKED_CAST") fun <T> Spinner.onItemSelected(func: (T) -> Unit) {
    onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<out Adapter>?) {}
        override fun onItemSelected(parent: AdapterView<out Adapter>, view: View?, position: Int, id: Long) =
            func(adapter.getItem(position) as T)
    }
}

@Suppress("UNCHECKED_CAST") fun <T> Spinner.onItemSelectedIgnoreFirst(func: (T) -> Unit) {
    var first = true
    onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<out Adapter>?) {}
        override fun onItemSelected(parent: AdapterView<out Adapter>, view: View?, position: Int, id: Long) {
            if(first)
                first = false
            else
                func(adapter.getItem(position) as T)
        }
    }
}