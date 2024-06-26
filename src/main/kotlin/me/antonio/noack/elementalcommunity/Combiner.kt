package me.antonio.noack.elementalcommunity

import java.util.*
import android.content.Context
import android.util.AttributeSet

class Combiner(ctx: Context, attributeSet: AttributeSet?) : UnlockedRows(ctx, attributeSet) {

    override fun onRecipeRequest(first: Element, second: Element) {
        thread {
            BasicOperations.askForCandidates(first, second, all,
                measuredWidth, measuredHeight,
                { add(first, second, it) }) {}
        }
    }

}