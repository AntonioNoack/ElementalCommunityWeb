/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ScaleGestureDetector
import android.view.ViewGroup
import androidx.core.math.MathUtils.clamp
import kotlinx.browser.localStorage
import me.antonio.noack.maths.MathsUtils.originalSpFactor
import me.antonio.noack.maths.MathsUtils.spFactor
import me.antonio.noack.webdroid.Runner
import org.w3c.dom.set
import kotlin.math.max
import kotlin.math.min

open class LinearLayout(context: Context?, attrs: AttributeSet? = null) : ViewGroup(context, attrs) {

    private var isVertical = true

    override fun onInit() {
        super.onInit()

        isVertical = attributeSet.getString("orientation", "") != "horizontal"

        if (getId() == "appZoom") {
            val scaleDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    val newScale = clamp(
                        spFactor * detector.scaleFactor,
                        0.1f * originalSpFactor,
                        10f * originalSpFactor
                    )
                    return if (newScale != spFactor) {
                        localStorage["spMultiplier"] = "${newScale / originalSpFactor}"
                        println("new scale: $spFactor")
                        spFactor = newScale
                        Runner.invalidateScale()
                        true
                    } else false
                }

                override fun onScaleBegin(detector: ScaleGestureDetector): Boolean = false
                override fun onScaleEnd(detector: ScaleGestureDetector) {}
            })
            setOnTouchListener { _, event -> scaleDetector.onTouchEvent(event) }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val isX = !isVertical
        val contentWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingX
        val contentHeight = MeasureSpec.getSize(heightMeasureSpec) - paddingY
        val modeW = MeasureSpec.getMode(widthMeasureSpec)
        val modeH = MeasureSpec.getMode(heightMeasureSpec)
        var sumW = 0
        var sumH = 0
        var weightSum = 0f
        val mode = if (isX) modeW else modeH
        val enableWeights = mode != MeasureSpec.UNSPECIFIED
        // when we are measured exactly, and have multiple children, they won't be measured exactly
        val childModeX = if (isX && modeW == MeasureSpec.EXACTLY && childCount > 1) MeasureSpec.AT_MOST else modeW
        val childModeY = if (!isX && modeH == MeasureSpec.EXACTLY && childCount > 1) MeasureSpec.AT_MOST else modeH
        for (child in children) { // measure all children as if they had enough space
            if (child.visibility == GONE) continue
            val lp = child.layoutParams
            child.measure(
                MeasureSpec.makeMeasureSpec(contentWidth - lp.marginX, childModeX),
                MeasureSpec.makeMeasureSpec(contentHeight - lp.marginY, childModeY)
            )
            if (isX) {
                sumH = max(child.measuredHeight + lp.marginY, sumH)
                if (enableWeights && lp.weight > 0f) weightSum += lp.weight
                else sumW += child.measuredWidth + lp.marginX
            } else {
                sumW = max(child.measuredWidth + lp.marginX, sumW)
                if (enableWeights && lp.weight > 0f) weightSum += lp.weight
                else sumH += child.measuredHeight + lp.marginY
            }
        }
        var wPerWeight = 0f
        var hPerWeight = 0f
        if (weightSum > 0f) {
            if (isX) { // remaining space / weight sum
                wPerWeight = max((contentWidth - sumW) / weightSum, 0f)
            } else {
                hPerWeight = max((contentHeight - sumH) / weightSum, 0f)
            }
        }
        var doneX = 0
        var doneY = 0
        val totalX =
            if (modeW != MeasureSpec.UNSPECIFIED) MeasureSpec.getSize(widthMeasureSpec) - paddingX
            else Int.MAX_VALUE
        val totalY =
            if (modeH != MeasureSpec.UNSPECIFIED) MeasureSpec.getSize(heightMeasureSpec) - paddingY
            else Int.MAX_VALUE
        for (ci in children.indices) {
            val child = children[ci]
            if (child.visibility == GONE) continue
            // calculate how much space we have
            val lp = child.layoutParams
            val childW = min(
                if (!isX || lp.weight <= 0f) child.measuredWidth
                else (lp.weight * wPerWeight).toInt(),
                totalX - doneX - lp.marginX
            )
            val childH = min(
                if (isX || lp.weight <= 0f) child.measuredHeight
                else (lp.weight * hPerWeight).toInt(),
                totalY - doneY - lp.marginY
            )
            // re-measure child
            child.measure(
                MeasureSpec.makeMeasureSpec(childW, if (isX) MeasureSpec.EXACTLY else modeW),
                MeasureSpec.makeMeasureSpec(childH, if (isX) modeH else MeasureSpec.EXACTLY)
            )
            // then place it and advance position
            placeChild(child, doneX, doneY, childW, childH)
            if (isX) {
                doneX += childW + lp.marginX
            } else {
                doneY += childH + lp.marginY
            }
        }
        val lp = layoutParams
        setMeasuredDimension(
            getDefaultSize(widthMeasureSpec, (if (isX) doneX else sumW) + paddingX, lp.width),
            getDefaultSize(heightMeasureSpec, (if (isX) sumH else doneY) + paddingY, lp.height)
        )
    }
}
