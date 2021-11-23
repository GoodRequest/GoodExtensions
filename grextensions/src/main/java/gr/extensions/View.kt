package gr.extensions

import android.app.Activity
import android.content.ContextWrapper
import android.content.Intent
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.concurrent.atomic.AtomicBoolean

fun View.visible()   { visibility = View.VISIBLE }
fun View.invisible() { visibility = View.INVISIBLE }
fun View.gone()      { visibility = View.GONE }

fun View.isVisible()    = visibility == View.VISIBLE
fun View.isNotVisible() = visibility != View.VISIBLE

fun View.visibleIf(condition: Boolean) { visibility = if(condition) View.VISIBLE else View.GONE }
fun View.invisibleIf(condition: Boolean) { visibility = if(condition) View.INVISIBLE else View.VISIBLE }
fun View.goneIf(condition: Boolean) = visibleIf(!condition)

fun View.color(@ColorRes id: Int) = ContextCompat.getColor(context, id)
fun View.drawable(@DrawableRes id: Int) = ContextCompat.getDrawable(context, id)!!
fun View.dimen(@DimenRes id: Int) = resources.getDimension(id)
fun View.string(@StringRes id: Int): String = resources.getString(id)

inline fun <reified T: Number> View.dimen(@DimenRes id: Int): T = with(dimen(id)) {
    when (T::class) {
        Float::class  -> this
        Double::class -> toDouble()
        Int::class    -> toInt()
        Long::class   -> toLong()
        else          -> throw IllegalArgumentException("Type ${T::class.simpleName} not supported")
    } as T
}

fun View.px(dp: Int) = (dp * (resources.displayMetrics.densityDpi / 160f)).toInt()
fun View.dp(px: Int) = (px / (resources.displayMetrics.densityDpi / 160f)).toInt()

inline infix fun View.onClick(crossinline action: () -> Unit) = setOnClickListener { action() }
inline infix fun View.onClickDebounce(crossinline action: () -> Unit) = debounceClick { action() }

fun View.toast(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

val View.activity: AppCompatActivity get() =
    if(context is AppCompatActivity) context as AppCompatActivity
    else (context as ContextWrapper).baseContext as AppCompatActivity

fun View.hideKeyboard() = context.inputMethodManager.hideSoftInputFromWindow(windowToken, 0)

inline fun <reified A: Activity> View.start() = context.startActivity(Intent(context, A::class.java))
inline fun <reified A: Activity> View.start(config: Intent.() -> Unit) = context.startActivity(Intent(context, A::class.java).apply(config))

inline fun <reified A: Activity> View.startClearTop() =
    context.startActivity(Intent(context, A::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
    })

inline fun <reified A: Activity> View.startClearTop(intentBuilder: Intent.() -> Unit = {}) =
    context.startActivity(Intent(context, A::class.java).apply(intentBuilder).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
    })

inline fun View.onAttachState(
    crossinline onAttach: () -> Unit,
    crossinline onDetach: () -> Unit) = addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {
    override fun onViewDetachedFromWindow(view: View) = onDetach()
    override fun onViewAttachedToWindow(view: View) = onAttach()
})

inline fun View.onAttach(crossinline action: () -> Unit) = onAttachState(action, {})
inline fun View.onDetach(crossinline action: () -> Unit) = onAttachState({}, action)

fun View.afterMeasure(callback: (Int, Int) -> Unit) {
    val currentWidth  = width
    val currentHeight = height

    if (currentWidth > 0 && currentHeight > 0) {
        callback(currentWidth, currentHeight)
        return
    }

    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            if (viewTreeObserver.isAlive)
                viewTreeObserver.removeOnPreDrawListener(this)

            callback(width, height)
            return true
        }
    })
}

fun View.showKeyboard(requestFocus: Boolean = false) {
    if (requestFocus) requestFocus()
    activity.inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.showKeyboardNotForced(requestFocus: Boolean = false) {
    if (requestFocus) requestFocus()
    activity.inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.setSize(width: Int, height: Int) {
    this.layoutParams = this.layoutParams.apply {
        this.width  = px(width)
        this.height = px(height)
    }
}

inline fun View.onClickPopup(@MenuRes menu: Int, crossinline actions: (Int) -> Unit) = onClick {
    with(PopupMenu(context, this)) {
        menuInflater.inflate(menu, this.menu)
        this.menu.getItem(0).title
        setOnMenuItemClickListener {
            actions(it.itemId)
            true }
        show()
    }
}

fun View.debounceClick(listener: ((View) -> Unit)?) {
    if (listener == null) {
        setOnClickListener(null)
    } else {
        setOnClickListener(object : DebouncingOnClickListener {
            override fun doClick(v: View) {
                listener(v)
            }
        })
    }
}

interface DebouncingOnClickListener : View.OnClickListener {
    companion object {
        private val enabled      = AtomicBoolean(true)
        private val postEnabling = Runnable { enabled.set(true) }
    }
    override fun onClick(v: View) {
        if (enabled.compareAndSet(true, false)) {
            v.postDelayed(postEnabling, 500)
            doClick(v)
        }
    }
    fun doClick(v: View)
}