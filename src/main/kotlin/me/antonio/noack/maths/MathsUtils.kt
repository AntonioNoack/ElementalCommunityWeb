package me.antonio.noack.maths

import org.w3c.dom.HTMLDivElement
import org.w3c.dom.get
import kotlinx.browser.document
import kotlinx.browser.localStorage

object MathsUtils {

    // min(window.screen.availWidth, window.screen.availHeight) / 1000f
    var spFactor: Float

    init {
        // get the default font size
        val body = document.body!!
        val who = document.createElement("div") as HTMLDivElement
        who.style.cssText = "display:inline-block; padding:0; line-height:1; position:absolute; visibility:hidden; font-size:1em"
        who.appendChild(document.createTextNode("M"))
        body.appendChild(who)
        val defaultFontSize = who.offsetHeight
        body.removeChild(who)
        spFactor = defaultFontSize.toFloat() / 15f
    }

    var originalSpFactor = spFactor

    init {
        spFactor *= localStorage["spMultiplier"]?.toFloatOrNull() ?: 1f
    }

    fun sq(x: Float, y: Float) = x*x+y*y

    fun spToPx(sp: Float): Float = sp * spFactor
    fun dpToPx(dp: Float): Float = dp * spFactor

}