package android.content

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

open class Context : FrameLayout(null, null) {

    val resources get() = Resources
    val layoutInflater get() = LayoutInflater

    open fun onCreate(savedInstanceState: Bundle?) {}
    open fun onBackPressed() {}
    open fun onDestroy() {}
    open fun onResume() {}
    open fun onPause() {}

    fun setContentView(layout: View) {
        children.clear()
        children.add(layout)
        layout.mParent = this
        layout.init()
    }

    fun getPreferences(mode: Int) = LocalStoragePreferences

    companion object {
        const val MODE_PRIVATE = 1
    }
}