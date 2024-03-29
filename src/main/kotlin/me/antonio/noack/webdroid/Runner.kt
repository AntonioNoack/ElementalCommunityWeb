package me.antonio.noack.webdroid

import R.all
import android.app.Dialog
import android.graphics.Canvas
import android.view.Event
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.math.MathUtils.clamp
import kotlinx.browser.document
import kotlinx.browser.window
import me.antonio.noack.maths.MathsUtils.sq
import org.w3c.dom.ALPHABETIC
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.CanvasTextBaseline
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.WheelEvent
import java.lang.StrictMath.pow
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import kotlin.js.Date
import kotlin.math.sqrt

// todo a button to change the size of dp/sp

object Runner {

    val toastView = ToastView(null, null)

    var invalidTime = 0

    fun invalidateScale() {
        all.invalidate()
        toastView.invalidate()
        all.init()
    }

    val touches = HashMap<dynamic, KtTouch>()

    lateinit var ctx: CanvasRenderingContext2D
    lateinit var canvas: Canvas

    private fun measure() {

        val width = window.innerWidth
        val height = window.innerHeight

        val widthMS = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val heightMS = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)

        all.measure(
            widthMS,
            heightMS
        )

        all.layout(0, 0, width, height)

    }

    private fun draw() {
        val save = canvas.save()
        all.draw(canvas)
        canvas.restoreToCount(save)
        val save3 = canvas.save()
        toastView.draw(canvas)
        canvas.restoreToCount(save3)
    }

    fun processEvent(event: Event) {
        all.processEvent(event)
    }

    // millis -> seconds
    fun now() = Date().getTime() * 1e-3

    private var oldInvalid = false
    private var invalidCounter = 0

    private fun render() {

        // lagging 1 mouse event behind, because we need notifications every frame

        var touchSquare = 0f
        for (touch in touches.values) {
            touchSquare += sq(touch.currentX - touch.lastX, touch.currentY - touch.lastY)
        }

        val threshold = 0.03

        if (sqrt(touchSquare) > threshold) {
            if (touchSquare > threshold) {
                touches.values.firstOrNull()?.apply {
                    processEvent(MotionEvent(currentX, currentY, MotionEvent.ACTION_MOVE))
                }
            }
        }

        val top = all

        val allIsInvalid = top.isInvalid == invalidTime || toastView.isInvalid == invalidTime
        if (allIsInvalid != oldInvalid) {
            oldInvalid = allIsInvalid
            invalidCounter = 1
        } else invalidCounter++

        if (allIsInvalid) {
            invalidTime++
            measure()
            draw()
        }
        requestAnimationFrame {
            render()
        }
    }

    fun onTouchEvent(e: TouchEvent) {
        for ((_, touch) in touches) {
            touch.isValid = false
        }
        var ctr = touches.size
        for (touch in e.touches) {
            var ktTouch = touches[touch.identifier]
            if (ktTouch == null) {
                ktTouch = KtTouch(touch.identifier, touch.clientX.toFloat(), touch.clientY.toFloat())
                touches[touch.identifier] = ktTouch
                processEvent(
                    MotionEvent(
                        ktTouch.lastX,
                        ktTouch.lastY,
                        if (touches.size > 1) MotionEvent.ACTION_POINTER_DOWN else MotionEvent.ACTION_DOWN
                    )
                )
            } else ctr--
            ktTouch.isValid = true
            ktTouch.lastX = touch.clientX.toFloat()
            ktTouch.lastY = touch.clientY.toFloat()
        }
        if (ctr > 0) {
            val toRemove = ArrayList<KtTouch>()
            for ((_, touch) in touches) {
                if (!touch.isValid) {
                    toRemove.add(touch)
                }
            }
            for (touch in toRemove) {
                processEvent(
                    MotionEvent(
                        touch.lastX,
                        touch.lastY,
                        if (touches.size > 1) MotionEvent.ACTION_POINTER_UP else MotionEvent.ACTION_UP
                    )
                )
                touches.remove(touch.id)
            }
        }
    }

    private var mouseButtons = 0
    private var currentMouseX = 0f
    private var currentMouseY = 0f

    fun init() {

        val width = window.innerWidth
        val height = window.innerHeight

        val canvasElement = document.getElementById("canvas") as HTMLCanvasElement
        canvasElement.width = width
        canvasElement.height = height

        ctx = canvasElement.getContext("2d") as CanvasRenderingContext2D
        ctx.textBaseline = CanvasTextBaseline.ALPHABETIC

        canvas = Canvas(ctx)
        val all = all

        all.init()
        all.onCreate(null)

        measure()

        all.invalidate()

        window.onresize = {
            canvasElement.width = window.innerWidth
            canvasElement.height = window.innerHeight
            all.invalidate()
        }

        fun updateMouse(mouseEvent: MouseEvent) {
            currentMouseX = mouseEvent.clientX.toFloat()
            currentMouseY = mouseEvent.clientY.toFloat()
            mouseButtons = mouseEvent.buttons.toInt()
        }

        canvasElement.onmousedown = { mouseEvent ->
            updateMouse(mouseEvent)
            processEvent(MotionEvent(currentMouseX, currentMouseY, MotionEvent.ACTION_DOWN))
            true
        }

        canvasElement.onmousemove = { mouseEvent ->
            updateMouse(mouseEvent)
            val type = if (mouseButtons == 0) MotionEvent.ACTION_HOVER_MOVE
            else MotionEvent.ACTION_MOVE
            processEvent(MotionEvent(currentMouseX, currentMouseY, type))
            true
        }

        canvasElement.onmouseup = { mouseEvent ->
            updateMouse(mouseEvent)
            processEvent(MotionEvent(currentMouseX, currentMouseY, MotionEvent.ACTION_UP))
            true
        }

        canvasElement.onwheel = { it: WheelEvent ->
            val deltaX = clamp(it.deltaX.toFloat(), -1f, 1f) * 51f
            val deltaY = clamp(it.deltaY.toFloat(), -1f, 1f) * 51f
            if (it.shiftKey || it.buttons != 0.toShort()) {
                // zoom
                val factor = pow(0.97f, deltaY / 17)
                processEvent(MotionEvent(currentMouseX, currentMouseY, factor, MotionEvent.ACTION_ZOOM))
            } else {
                processEvent(MotionEvent(currentMouseX, currentMouseY, deltaX, deltaY, MotionEvent.ACTION_SCROLL))
            }
        }

        canvasElement.addEventListener("touchstart", { e ->
            e.preventDefault()
            e as TouchEvent
            onTouchEvent(e.originalEvent ?: e)
        })

        canvasElement.addEventListener("touchmove", { e ->
            e.preventDefault()
            e as TouchEvent
            onTouchEvent(e.originalEvent ?: e)
        })

        canvasElement.addEventListener("touchend", { e ->
            e.preventDefault()
            e as TouchEvent
            onTouchEvent(e.originalEvent ?: e)
        })

        window.onkeydown = {
            when (it.key.toLowerCase()) {
                "escape" -> {
                    val dialog = all.children.filterIsInstance<Dialog>().lastOrNull()
                    if (dialog != null) {
                        if (dialog.isCancelable) {
                            dialog.dismiss()
                        }
                    } else {
                        all.onBackPressed()
                    }
                }
            }
        }

        window.onkeyup = {
            when (it.key.toLowerCase()) {
                "l" -> {
                    // layout
                    println(all.toStringWithVisibleChildren())
                }
                "t" -> {
                    // Toast.makeText(all, "Test", Toast.LENGTH_SHORT).show()
                }
            }
        }

        render()
    }
}

fun main() {
    Runner.init()
    Toast.makeText(all, "Get the app in the PlayStore!", Toast.LENGTH_SHORT).show()
    setTimeout({
        Toast.makeText(all, "Use mouse wheel + shift to zoom.", Toast.LENGTH_SHORT).show()
    }, 4000)
}

// todo needs to be fixed before release:
//  - scrollbars cannot be dragged
//  - some centered text isn't centered, e.g. in "Random Suggestion"

// todo remove before release
//  - debug printing events
//  - other debug printing?