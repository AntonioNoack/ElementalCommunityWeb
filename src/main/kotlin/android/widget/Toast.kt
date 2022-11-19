package android.widget

import android.content.Context
import me.antonio.noack.webdroid.Runner
import me.antonio.noack.webdroid.Runner.now

object Toast {

    class ToastImpl(val msg: String, val length: Int){
        fun show(){
            val toastView = Runner.toastView
            toastView.text = msg
            toastView.time = 0f
            toastView.isDone = false
            toastView.lastTime = now()
            toastView.duration = when(length){
                LENGTH_LONG -> 10f
                LENGTH_SHORT -> 3f
                LENGTH_INSTANT -> {
                    toastView.time = toastView.inTime
                    3f + toastView.inTime
                }
                else -> 5f
            }
            toastView.invalidate()
        }
    }

    fun makeText(ctx: Context? = null, msg: String, length: Int): ToastImpl {
        return ToastImpl(msg, length)
    }

    fun makeText(ctx: Context?, msg: Int, length: Int): ToastImpl {
        return ToastImpl(msg.toString(), length)
    }

    const val LENGTH_LONG = 1
    const val LENGTH_SHORT = 2
    const val LENGTH_INSTANT = 3



}