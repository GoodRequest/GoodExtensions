package gr.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

val ViewGroup.children: List<View> get() = (0 until childCount).map(::getChildAt)
fun ViewGroup.visibleChild(id: Int) = children.forEach { it.visibleIf(it.id == id) }
fun ViewGroup.inflate(id: Int, parent: ViewGroup = this): View = LayoutInflater.from(context).inflate(id, parent, false)

fun ViewGroup.replaceContent(views: List<View>) {
    removeAllViews()
    views.forEach(this::addView)
}
