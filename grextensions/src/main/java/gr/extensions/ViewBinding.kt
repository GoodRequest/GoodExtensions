package gr.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

fun ViewBinding.color(@ColorRes id: Int) = root.color(id)
fun ViewBinding.drawable(@DrawableRes id: Int) = root.drawable(id)
fun ViewBinding.dimen(@DimenRes id: Int) = root.dimen(id)
fun ViewBinding.string(@StringRes id: Int): String = root.string(id)
fun ViewBinding.string(@StringRes id: Int, vararg args: Any): String = root.string(id, *args)
inline fun <reified T: Number> ViewBinding.dimen(@DimenRes id: Int): T = root.dimen<T>(id)


inline fun <reified A: Activity> ViewBinding.start() = root.start<A>()
inline fun <reified A: Activity> ViewBinding.start(config: Intent.() -> Unit) = root.start<A>(config)

inline fun <reified A: Activity> ViewBinding.startClearTop() = root.startClearTop<A>()
inline fun <reified A: Activity> ViewBinding.startClearTop(intentBuilder: Intent.() -> Unit = {}) = root.startClearTop<A>(intentBuilder)


fun ViewBinding.toast(message: String) = Toast.makeText(root.context, message, Toast.LENGTH_SHORT).show()

val ViewBinding.activity : AppCompatActivity get() = root.activity
val ViewBinding.context  : Context           get() = root.context
val ViewBinding.intent   : Intent            get() = activity.intent

inline fun ViewBinding.onCreate (crossinline action: () -> Unit) = activity.lifecycle.onCreate(action)
inline fun ViewBinding.onResume (crossinline action: () -> Unit) = activity.lifecycle.onResume(action)
inline fun ViewBinding.onStart  (crossinline action: () -> Unit) = activity.lifecycle.onStart(action)
inline fun ViewBinding.onPause  (crossinline action: () -> Unit) = activity.lifecycle.onPause(action)
inline fun ViewBinding.onStop   (crossinline action: () -> Unit) = activity.lifecycle.onStop(action)
inline fun ViewBinding.onDestroy(crossinline action: () -> Unit) = activity.lifecycle.onDestroy(action)