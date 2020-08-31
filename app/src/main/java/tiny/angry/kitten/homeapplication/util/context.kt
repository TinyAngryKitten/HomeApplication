package tiny.angry.kitten.homeapplication.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner


fun Context.lifecycleOwner(): LifecycleOwner? {
    var curContext = this
    var maxDepth = 20
    while (maxDepth-- > 0 && curContext !is LifecycleOwner) {
        curContext = (curContext as ContextWrapper).baseContext
    }
    return if (curContext is LifecycleOwner) {
        curContext as LifecycleOwner
    } else {
        null
    }
}

fun Context.fragmentActivity(): FragmentActivity? {
    var curContext = this
    var maxDepth = 20
    while (--maxDepth > 0 && curContext !is FragmentActivity) {
        curContext = (curContext as ContextWrapper).baseContext
    }
    return if(curContext is FragmentActivity)
        curContext
    else
        null
}

/**
 * Get activity instance from desired context.
 */
fun Context.getActivity(): Activity? {
    if (this is Activity) return this
    return if (this is ContextWrapper) baseContext.getActivity() else null
}