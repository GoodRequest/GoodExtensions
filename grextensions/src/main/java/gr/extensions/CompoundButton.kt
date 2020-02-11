package gr.extensions

import android.widget.CompoundButton

inline fun CompoundButton.onCheckChanged(crossinline action: (Boolean) -> Unit) = setOnCheckedChangeListener { _, state -> action(state) }
inline fun CompoundButton.onChecked(crossinline action: () -> Unit) = onCheckChanged { if(it) action() }

fun CompoundButton.setCheckedNoAnimation(checked: Boolean) {
    isChecked = checked
    jumpDrawablesToCurrentState()
}