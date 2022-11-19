package android.app

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

abstract class Dialog(context: Context?, attributeSet: AttributeSet?): LinearLayout(context, attributeSet) {

    var isCancelable = true

    abstract fun dismiss()

}