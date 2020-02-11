package gr.extensions

import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar

inline fun Toolbar.menu(@MenuRes resource: Int, crossinline actions: (id: Int) -> Unit) {
    menu.clear()
    inflateMenu(resource)
    setOnMenuItemClickListener { actions(it.itemId); true }
}