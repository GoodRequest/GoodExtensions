package gr.extensions

import android.app.Activity
import android.content.Intent
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.LayoutRes

val Activity.screenWidthPixels get() = DisplayMetrics().apply { windowManager.defaultDisplay.getMetrics(this) }.widthPixels
val Activity.screenHeightPixels get() = DisplayMetrics().apply { windowManager.defaultDisplay.getMetrics(this) }.heightPixels

inline fun <reified T: Any> Activity.start() = startActivity(Intent(this, T::class.java))
inline fun <reified A: Activity> Activity.start(config: Intent.() -> Unit) = startActivity(Intent(this, A::class.java).apply(config))

fun Activity.hideKeyboard() {
    inputMethodManager.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)
}

fun Activity.fullscreen() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
}

fun Activity.finishWithoutAnimation() {
    finish()
    overridePendingTransition(0, 0)
}

fun Activity.contentView(@LayoutRes layout: Int, viewLogic: View.() -> Unit) =
    layoutInflater.inflate(layout, null).apply(viewLogic).let(this::setContentView)