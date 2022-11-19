package me.antonio.noack.webdroid

import R.all
import android.app.Dialog
import android.graphics.Canvas
import android.view.*
import android.widget.Toast
import androidx.core.math.MathUtils.clamp
import me.antonio.noack.maths.MathsUtils.sq
import org.w3c.dom.*
import org.w3c.dom.events.WheelEvent
import java.lang.StrictMath.pow
import java.util.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlin.js.Date
import kotlin.math.sqrt

// todo a project that copies our libraries, so we can still use Intellij Idea :)
// todo a project with efficient light calculation for static objects (ambient occlusion backing),
// todo or just import to blender and bake there? nah, data is not well handable...


// todo focus

// todo shift + scroll = zooming
// todo real touch events...

// todo why do usual mouse events work, but touches don't?

// todo a button to change the size of dp/sp
// done middle button to zoom?





object Runner {

    val toastView = ToastView(null, null)

    var touchedView: View? = null

    var invalidTime = 0
    var oldStackSize = -1

    // val invalidViews = ArrayList<View>()

    fun invalidateScale(){
        all.invalidate()
        for(dialog in dialogStack){
            dialog.invalidate()
        }
        toastView.invalidate()
        all.init()
    }

    val dialogStack = Stack<Dialog>()
    fun invalidate(){
        // todo redraw...
    }

    val touches = HashMap<dynamic, KtTouch>()

    lateinit var ctx: CanvasRenderingContext2D
    lateinit var canvas: Canvas

    var scrollGoalX = 0f
    var scrollGoalY = 0f

    private fun measure(){

        val width = window.innerWidth
        val height = window.innerHeight

        val widthMS = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val heightMS = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)

        all.measure(
                widthMS,
                heightMS)

        for(dialog in dialogStack){
            if(dialog.isInvalid == invalidTime-1){
                dialog.measure(widthMS, heightMS)
            }
        }

        all.mLeft = 0
        all.mTop = 0
        all.mRight = width
        all.mBottom = height

    }

    private fun draw(){

        val save = canvas.save()
        // canvas.translate(100f, 100f)
        // canvas.ctx.clearRect(0.0, 0.0, window.innerWidth.toDouble(), window.innerHeight.toDouble())
        all.draw(canvas)
        canvas.restoreToCount(save)

        for(dialog in dialogStack){
            val save2 = canvas.save()
            dialog.draw(canvas)
            canvas.restoreToCount(save2)
        }

        val save3 = canvas.save()
        toastView.draw(canvas)
        canvas.restoreToCount(save3)

    }

    fun processEvent(event: Event){
        val container = dialogStack.peek() ?: all
        if(event is MotionEvent){
            if(event.actionMasked == MotionEvent.ACTION_DOWN){
                touchedView = container.processEvent(event)
                return
            } else if(when(event.actionMasked){
                        MotionEvent.ACTION_MOVE, MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_POINTER_UP,
                            MotionEvent.ACTION_UP -> true
                        else -> false
                    }){
                //if(event.actionMasked != MotionEvent.ACTION_MOVE){
                //    println("tv: ${touchedView?.getId()}, ${event.actionMasked}")
                //}
                val touchedView = touchedView
                if(touchedView != null){
                    val sx = event.x
                    val sy = event.y
                    val touchMap = HashMap<View, Pair<Int, Int>>()
                    touchedView.runParentsThenSelf {
                        event.dx += it.mLeft
                        event.dy += it.mTop
                        touchMap[it] = event.dx to event.dy
                    }
                    var view = touchedView
                    tree@ while(view != null){
                        val position = touchMap[view] ?: break@tree
                        event.dx = position.first
                        event.dy = position.second
                        event.x = sx - event.dx
                        event.y = sy - event.dy
                        if(event.actionMasked != MotionEvent.ACTION_MOVE || (event.x.toInt() in 0 until view.measuredWidth && event.y.toInt() in 0 until view.measuredHeight)){
                            if(event.call(view)){
                                break@tree
                            }
                        }
                        view = view.mParent
                    }
                    return
                }
            }
        }
        container.processEvent(event)
    }

    // millis -> seconds
    fun now() = Date().getTime() * 1e-3

    private var oldInvalid = false
    private var invalidCounter = 0

    fun scroll(dx: Float, dy: Float){
        processEvent(MotionEvent(targetMouseX, targetMouseY, dx, dy, MotionEvent.ACTION_SCROLL))
    }

    private fun render(){

        /*val length = invalidViews.size
        for(i in 0 until length){
            val view = invalidViews[i]
            val parent = view.mParent
            if(view.isInvalid && parent?.isInvalid != true){

            }
        }*/

        val workPerFrame = 0.1f
        val fm1WPF = 1f - workPerFrame

        val touchMargin = 10f

        // lagging 1 mouse event behind, because we need notifications every frame
        val mouseX = targetMouseX + clamp((lastMouseX - targetMouseX) * fm1WPF, -touchMargin, touchMargin)
        val mouseY = targetMouseY + clamp((lastMouseY - targetMouseY) * fm1WPF, -touchMargin, touchMargin)

        // document.title = "$mouseY"

        var touchSquare = 0f
        for(touch in touches.values){
            touchSquare += sq(touch.currentX - touch.targetX, touch.currentY - touch.targetY)
        }

        val motionSquare = sq(mouseX - targetMouseX, mouseY - targetMouseY)
        val scrollSquare = sq(scrollGoalX, scrollGoalY)

        val threshold = 0.03

        if(sqrt(motionSquare + scrollSquare + touchSquare) > threshold){

            // mouse is moving
            if(motionSquare > threshold){
                if(mouseButtons != 0){
                    val event = MotionEvent(mouseX, mouseY, MotionEvent.ACTION_MOVE)
                    processEvent(event)
                } else {
                    val event = MotionEvent(mouseX, mouseY, MotionEvent.ACTION_HOVER_MOVE)
                    processEvent(event)
                }
            }

            // wheel is moving
            if(scrollSquare > threshold){
                val maxMargin = 300
                val deltaScrollX = clamp(scrollGoalX * workPerFrame, scrollGoalX - maxMargin, scrollGoalX + maxMargin)
                scrollGoalX -= deltaScrollX
                val deltaScrollY = clamp(scrollGoalY * workPerFrame, scrollGoalY - maxMargin, scrollGoalY + maxMargin)
                scrollGoalY -= deltaScrollY
                scroll(deltaScrollX, deltaScrollY)
            }

            if(touchSquare > threshold){
                for(touch in touches.values){
                    touch.currentX = touch.targetX + clamp((touch.currentX - touch.targetX) * fm1WPF, -touchMargin, touchMargin)
                    touch.currentY = touch.targetY + clamp((touch.currentY - touch.targetY) * fm1WPF, -touchMargin, touchMargin)
                }
                touches.values.firstOrNull()?.apply {
                    processEvent(MotionEvent(currentX, currentY, MotionEvent.ACTION_MOVE))
                }
            }

        }

        lastMouseX = mouseX
        lastMouseY = mouseY

        val top = dialogStack.peek() ?: all

        val allIsInvalid = top.isInvalid == invalidTime || oldStackSize != dialogStack.size() || toastView.isInvalid == invalidTime
        oldStackSize = dialogStack.size()

        if(allIsInvalid != oldInvalid){
            // println("invalid? $oldInvalid x $invalidCounter")
            oldInvalid = allIsInvalid
            invalidCounter = 1
        } else invalidCounter++

        if(allIsInvalid){
            // val time0 = now()
            invalidTime++
            measure()
            draw()
            // val time1 = now()
            // println("dt ${time1-time0} ${all.toStringWithVisibleChildren()}")
        }

        requestAnimationFrame {
            render()
        }

    }

    fun onTouchEvent(e: TouchEvent){
        for((_, touch) in touches){
            touch.isValid = false
        }
        var ctr = touches.size
        for(touch in e.touches){
            var ktTouch = touches[touch.identifier]
            if(ktTouch == null){
                ktTouch = KtTouch(touch.identifier, touch.clientX.toFloat(), touch.clientY.toFloat())
                touches[touch.identifier] = ktTouch
                // println("down at ${ktTouch.currentX}, ${ktTouch.currentY}")
                processEvent(MotionEvent(ktTouch.targetX, ktTouch.targetY, if(touches.size > 1) MotionEvent.ACTION_POINTER_DOWN  else MotionEvent.ACTION_DOWN))
            } else ctr--
            ktTouch.isValid = true
            ktTouch.targetX = touch.clientX.toFloat()
            ktTouch.targetY = touch.clientY.toFloat()
        }
        if(ctr > 0){
            val toRemove = ArrayList<KtTouch>()
            for((_, touch) in touches){
                if(!touch.isValid){
                    toRemove.add(touch)
                }
            }
            for(touch in toRemove){
                // println("up at ${touch.targetX}, ${touch.targetY}")
                processEvent(MotionEvent(touch.targetX, touch.targetY, if(touches.size > 1) MotionEvent.ACTION_POINTER_UP else MotionEvent.ACTION_UP))
                touches.remove(touch.id)
            }
        }
    }

    private var lastMouseX = 0f
    private var lastMouseY = 0f
    private var targetMouseX = 0f
    private var targetMouseY = 0f

    var mouseButtons = 0

    fun init(){

        val width = window.innerWidth
        val height = window.innerHeight

        val canvasElement = document.getElementById("canvas") as HTMLCanvasElement
        canvasElement.width = width
        canvasElement.height = height

        ctx = canvasElement.getContext("2d") as CanvasRenderingContext2D

        println("got ctx")

        ctx.textBaseline = CanvasTextBaseline.ALPHABETIC

        canvas = Canvas(ctx)
        val all = all

        all.init()
        all.onCreate(null)

        println("created canvas")

        Runner.measure()

        println("measured")
        println(all)

        // all.isReady = true
        all.invalidate()

        var downTime = 0.0

        window.onresize = {
            canvasElement.width = window.innerWidth
            canvasElement.height = window.innerHeight
            all.invalidate()
            for(dialog in dialogStack){
                dialog.invalidate()
            }
        }

        canvasElement.onmousedown = {mouseEvent ->
            targetMouseX = mouseEvent.clientX.toFloat()
            targetMouseY = mouseEvent.clientY.toFloat()
            mouseButtons = mouseEvent.buttons.toInt()
            val event = MotionEvent(mouseEvent.clientX.toFloat(), mouseEvent.clientY.toFloat(), MotionEvent.ACTION_DOWN)
            processEvent(event)
            downTime = now()
            true
        }

        canvasElement.onmousemove = { mouseEvent ->
            targetMouseX = mouseEvent.clientX.toFloat()
            targetMouseY = mouseEvent.clientY.toFloat()
            mouseButtons = mouseEvent.buttons.toInt()
            true
        }

        canvasElement.onmouseup = { mouseEvent ->
            targetMouseX = mouseEvent.clientX.toFloat()
            targetMouseY = mouseEvent.clientY.toFloat()
            mouseButtons = mouseEvent.buttons.toInt()
            val event = MotionEvent(mouseEvent.clientX.toFloat(), mouseEvent.clientY.toFloat(), MotionEvent.ACTION_UP)
            processEvent(event)
            true
        }

        /*canvasElement.onclick = {
            val now = now()
            val dt = now - downTime
            // processEvent(ClickEvent(it, dt > 0.5))
            // processEvent(MotionEvent(it.clientX*1f, it.clientY*1f, MotionEvent.ACTION_DOWN))
            processEvent(MotionEvent(it.clientX*1f, it.clientY*1f, MotionEvent.ACTION_UP))
            true
        }*/

        var scrollX = 0.0

        canvasElement.onwheel = { it: WheelEvent ->

            val deltaY = clamp(it.deltaY, -1.0, 1.0) * 51

            if(it.shiftKey || it.buttons != 0.toShort()){
                // zoom
                val factor = pow(0.97f, deltaY.toFloat() / 17)
                println("${it.deltaY} -> $factor")
                processEvent(MotionEvent(lastMouseX, lastMouseY, factor, MotionEvent.ACTION_ZOOM))
            } else {
                scrollGoalX += (clamp(it.deltaX, -1.0, 1.0) * 51).toFloat()
                scrollGoalY += deltaY.toFloat()
                scrollX += deltaY
            }

            // println("scrollX $scrollX")
            // all.invalidate()
        }

        canvasElement.addEventListener("touchstart", { e ->
            e.preventDefault()
            e as TouchEvent
            onTouchEvent(e.originalEvent ?: e)
            // println("ts")
            // Toast.makeText(all, "s${moveCtr++}", Toast.LENGTH_INSTANT).show()
        })

        canvasElement.addEventListener("touchmove", { e ->
            e.preventDefault()
            e as TouchEvent
            onTouchEvent(e.originalEvent ?: e)
            // println("tm")
            // Toast.makeText(all, "${moveCtr++} ${e.touches.length} ${e.touches}", Toast.LENGTH_INSTANT.toInt()).show()
        })

        canvasElement.addEventListener("touchend", { e ->
            e.preventDefault()
            e as TouchEvent
            onTouchEvent(e.originalEvent ?: e)
            // println("te")
            // Toast.makeText(all, "e${moveCtr++}", Toast.LENGTH_INSTANT).show()
        })

        window.onkeydown = {
            when(it.key.toLowerCase()){
                "escape" -> {
                    val dialog = dialogStack.peek()
                    if(dialog != null){
                        if(dialog.isCancelable){
                            dialog.dismiss()
                            // all.invalidate()
                        }
                    } else {
                        all.onBackPressed()
                    }
                }
                // else -> println("Key ${it.key}")
            }
        }

        window.onkeyup = {
            when(it.key.toLowerCase()){
                "l" -> {
                    // layout
                    if(it.shiftKey){
                        println((dialogStack.peek() ?: all).toStringWithVisibleChildren())
                    } else {
                        println(all.toStringWithVisibleChildren())
                    }
                }
                "t" -> {
                    // Toast.makeText(all, "Test", Toast.LENGTH_SHORT).show()
                }
            }
        }

        render()

    }

}


fun main(){
    Runner.init()
    Toast.makeText(all, "Get the app in the PlayStore!", Toast.LENGTH_SHORT).show()
    setTimeout({
        Toast.makeText(all, "Use mouse wheel + shift to zoom.", Toast.LENGTH_SHORT).show()
    }, 4000)
}

