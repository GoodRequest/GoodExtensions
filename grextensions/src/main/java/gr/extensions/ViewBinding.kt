package gr.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding

fun ViewBinding.dimen(@DimenRes id: Int) = root.dimen(id)
fun ViewBinding.string(@StringRes id: Int): String = root.string(id)
fun ViewBinding.string(@StringRes id: Int, vararg args: Any): String = root.string(id, *args)
inline fun <reified T: Number> ViewBinding.dimen(@DimenRes id: Int): T = root.dimen<T>(id)


inline fun <reified A: Activity> ViewBinding.start() = root.start<A>()
inline fun <reified A: Activity> ViewBinding.start(config: Intent.() -> Unit) = root.start<A>(config)

inline fun <reified A: Activity> ViewBinding.startClearTop() = root.startClearTop<A>()
inline fun <reified A: Activity> ViewBinding.startClearTop(intentBuilder: Intent.() -> Unit = {}) = root.startClearTop<A>(intentBuilder)


fun ViewBinding.toast(message: String) = Toast.makeText(root.context, message, Toast.LENGTH_SHORT).show()

val ViewBinding.activity : Activity get() = root.activity
val ViewBinding.context  : Context  get() = root.context
val ViewBinding.intent   : Intent   get() = activity.intent