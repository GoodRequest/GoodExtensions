package gr.extensions

import android.transition.Transition

inline fun Transition.onTransitionEnd(crossinline action: () -> Unit) {
    addListener(object: Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition?) = action()
        override fun onTransitionResume(transition: Transition?) {}
        override fun onTransitionPause(transition: Transition?) {}
        override fun onTransitionCancel(transition: Transition?) {}
        override fun onTransitionStart(transition: Transition?) {}
    })
}