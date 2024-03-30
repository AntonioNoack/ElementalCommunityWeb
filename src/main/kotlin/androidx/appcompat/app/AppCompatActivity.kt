package androidx.appcompat.app

import android.content.Context
import android.os.Bundle
import android.view.View

open class AppCompatActivity : Context() {

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
}