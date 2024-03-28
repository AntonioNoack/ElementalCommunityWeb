package android.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View

class ImageView(ctx: Context, attributeSet: AttributeSet?): View(ctx, attributeSet){

    var drawable: Drawable? = null

    override fun onInit() {
        super.onInit()

        // load the image
        drawable = attributeSet.getDrawable("src", null)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val drawable = drawable ?: return
        canvas.translate(mPaddingLeft, mPaddingTop)
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight())
        drawable.draw(canvas)
    }

}