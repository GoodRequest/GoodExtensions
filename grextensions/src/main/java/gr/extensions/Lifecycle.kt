package gr.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

inline fun Lifecycle.onEvent(event: Lifecycle.Event, crossinline action: () -> Unit): Unit =
    addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, currentEvent: Lifecycle.Event) {
            if (event == currentEvent) action()
        }
    })

inline fun Lifecycle.onCreate (crossinline action: () -> Unit) = onEvent(Lifecycle.Event.ON_CREATE,  action = action)
inline fun Lifecycle.onResume (crossinline action: () -> Unit) = onEvent(Lifecycle.Event.ON_RESUME,  action = action)
inline fun Lifecycle.onStart  (crossinline action: () -> Unit) = onEvent(Lifecycle.Event.ON_START,   action = action)
inline fun Lifecycle.onPause  (crossinline action: () -> Unit) = onEvent(Lifecycle.Event.ON_PAUSE,   action = action)
inline fun Lifecycle.onStop   (crossinline action: () -> Unit) = onEvent(Lifecycle.Event.ON_STOP,    action = action)
inline fun Lifecycle.onDestroy(crossinline action: () -> Unit) = onEvent(Lifecycle.Event.ON_DESTROY, action = action)