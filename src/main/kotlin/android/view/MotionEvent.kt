package android.view

import me.antonio.noack.webdroid.Runner.touches
import me.antonio.noack.webdroid.TouchEvent

open class MotionEvent(x: Float, y: Float, val motionDX: Float, val motionDY: Float, val zoom: Float, val action: Int): Event(x, y){

    constructor(x: Float, y: Float, action: Int): this(x, y, 0f, 0f, 0f, action)
    constructor(x: Float, y: Float, zoom: Float, action: Int): this(x, y, 0f, 0f, zoom, action)
    constructor(x: Float, y: Float, dx: Float, dy: Float, action: Int): this(x, y, dx, dy, 0f, action)
    constructor(e: TouchEvent): this(e.clientX.toFloat(), e.clientY.toFloat(), when(e.type.toLowerCase()){
        "touchstart" -> ACTION_DOWN
        "touchmove" -> ACTION_MOVE
        "touchend" -> ACTION_UP
        else -> 0 // idk...
    })

    val originalX = x
    val originalY = y

    var actionMasked = action
    var source = 1

    fun isTouchEvent() = when(actionMasked){
        ACTION_SCROLL, ACTION_HOVER_ENTER, ACTION_HOVER_EXIT,
        ACTION_HOVER_MOVE -> false
        else -> true
    }

    fun getDownTime(){
        // todo get the time when the user pressed down
    }

    fun getEventTime() = (time*1e3).toLong()
    fun getEventTimeNano() = (time*1e9).toLong()

    fun getX() = x
    fun getY() = y

    fun getX(index: Int) = touches[index]?.currentX ?: x
    fun getY(index: Int) = touches[index]?.currentY ?: y

    // fun getPressure()
    // fun getSize()

    val pointerCount
        get() = touches.size

    fun getPointerCount() = touches.size
    fun getPointerId(index: Int) = touches[index]?.id










    fun getAxisValue(axis: Int) = 0f

    override fun call(view: View): Boolean {
        // if(view.touchListener != null && actionMasked != MotionEvent.ACTION_MOVE && actionMasked != MotionEvent.ACTION_HOVER_MOVE) println("touched ${view.getId()}")
        return view.touchListener?.invoke(view, this) ?: false
    }

    override fun toString(): String = "MotionEvent[$x $y $actionMasked]"

    companion object {

        /**
         * An invalid pointer id.
         *
         * This value (-1) can be used as a placeholder to indicate that a pointer id
         * has not been assigned or is not available.  It cannot appear as
         * a pointer id inside a [MotionEvent].
         */
        const val INVALID_POINTER_ID = -1

        /**
         * Bit mask of the parts of the action code that are the action itself.
         */
        const val ACTION_MASK = 0xff

        /**
         * Constant for [.getActionMasked]: A pressed gesture has started, the
         * motion contains the initial starting location.
         *
         *
         * This is also a good time to check the button state to distinguish
         * secondary and tertiary button clicks and handle them appropriately.
         * Use [.getButtonState] to retrieve the button state.
         *
         */
        const val ACTION_DOWN = 0

        /**
         * Constant for [.getActionMasked]: A pressed gesture has finished, the
         * motion contains the final release location as well as any intermediate
         * points since the last down or move event.
         */
        const val ACTION_UP = 1

        /**
         * Constant for [.getActionMasked]: A change has happened during a
         * press gesture (between [.ACTION_DOWN] and [.ACTION_UP]).
         * The motion contains the most recent point, as well as any intermediate
         * points since the last down or move event.
         */
        const val ACTION_MOVE = 2

        /**
         * Constant for [.getActionMasked]: The current gesture has been aborted.
         * You will not receive any more points in it.  You should treat this as
         * an up event, but not perform any action that you normally would.
         */
        const val ACTION_CANCEL = 3

        /**
         * Constant for [.getActionMasked]: A movement has happened outside of the
         * normal bounds of the UI element.  This does not provide a full gesture,
         * but only the initial location of the movement/touch.
         *
         *
         * Note: Because the location of any event will be outside the
         * bounds of the view hierarchy, it will not get dispatched to
         * any children of a ViewGroup by default. Therefore,
         * movements with ACTION_OUTSIDE should be handled in either the
         * root [View] or in the appropriate [Window.Callback]
         * (e.g. [android.app.Activity] or [android.app.Dialog]).
         *
         */
        const val ACTION_OUTSIDE = 4

        /**
         * Constant for [.getActionMasked]: A non-primary pointer has gone down.
         *
         *
         * Use [.getActionIndex] to retrieve the index of the pointer that changed.
         *
         *
         * The index is encoded in the [.ACTION_POINTER_INDEX_MASK] bits of the
         * unmasked action returned by [.getAction].
         *
         */
        const val ACTION_POINTER_DOWN = 5

        /**
         * Constant for [.getActionMasked]: A non-primary pointer has gone up.
         *
         *
         * Use [.getActionIndex] to retrieve the index of the pointer that changed.
         *
         *
         * The index is encoded in the [.ACTION_POINTER_INDEX_MASK] bits of the
         * unmasked action returned by [.getAction].
         *
         */
        const val ACTION_POINTER_UP = 6

        /**
         * Constant for [.getActionMasked]: A change happened but the pointer
         * is not down (unlike [.ACTION_MOVE]).  The motion contains the most
         * recent point, as well as any intermediate points since the last
         * hover move event.
         *
         *
         * This action is always delivered to the window or view under the pointer.
         *
         *
         * This action is not a touch event so it is delivered to
         * [View.onGenericMotionEvent] rather than
         * [View.onTouchEvent].
         *
         */
        const val ACTION_HOVER_MOVE = 7

        /**
         * Constant for [.getActionMasked]: The motion event contains relative
         * vertical and/or horizontal scroll offsets.  Use [.getAxisValue]
         * to retrieve the information from [.AXIS_VSCROLL] and [.AXIS_HSCROLL].
         * The pointer may or may not be down when this event is dispatched.
         *
         *
         * This action is always delivered to the window or view under the pointer, which
         * may not be the window or view currently touched.
         *
         *
         * This action is not a touch event so it is delivered to
         * [View.onGenericMotionEvent] rather than
         * [View.onTouchEvent].
         *
         */
        const val ACTION_SCROLL = 8

        /**
         * Constant for [.getActionMasked]: The pointer is not down but has entered the
         * boundaries of a window or view.
         *
         *
         * This action is always delivered to the window or view under the pointer.
         *
         *
         * This action is not a touch event so it is delivered to
         * [View.onGenericMotionEvent] rather than
         * [View.onTouchEvent].
         *
         */
        const val ACTION_HOVER_ENTER = 9

        /**
         * Constant for [.getActionMasked]: The pointer is not down but has exited the
         * boundaries of a window or view.
         *
         *
         * This action is always delivered to the window or view that was previously under the pointer.
         *
         *
         * This action is not a touch event so it is delivered to
         * [View.onGenericMotionEvent] rather than
         * [View.onTouchEvent].
         *
         */
        const val ACTION_HOVER_EXIT = 10

        /**
         * Constant for [.getActionMasked]: A button has been pressed.
         *
         *
         *
         * Use [.getActionButton] to get which button was pressed.
         *
         *
         * This action is not a touch event so it is delivered to
         * [View.onGenericMotionEvent] rather than
         * [View.onTouchEvent].
         *
         */
        const val ACTION_BUTTON_PRESS = 11

        /**
         * Constant for [.getActionMasked]: A button has been released.
         *
         *
         *
         * Use [.getActionButton] to get which button was released.
         *
         *
         * This action is not a touch event so it is delivered to
         * [View.onGenericMotionEvent] rather than
         * [View.onTouchEvent].
         *
         */
        const val ACTION_BUTTON_RELEASE = 12

        const val ACTION_ZOOM = 13

        /**
         * Bits in the action code that represent a pointer index, used with
         * [.ACTION_POINTER_DOWN] and [.ACTION_POINTER_UP].  Shifting
         * down by [.ACTION_POINTER_INDEX_SHIFT] provides the actual pointer
         * index where the data for the pointer going up or down can be found; you can
         * get its identifier with [.getPointerId] and the actual
         * data with [.getX] etc.
         *
         * @see .getActionIndex
         */
        const val ACTION_POINTER_INDEX_MASK = 0xff00

        /**
         * Bit shift for the action bits holding the pointer index as
         * defined by [.ACTION_POINTER_INDEX_MASK].
         *
         * @see .getActionIndex
         */
        const val ACTION_POINTER_INDEX_SHIFT = 8


        @Deprecated("Use {@link #ACTION_POINTER_INDEX_MASK} to retrieve the\n" +
                "      data index associated with {@link #ACTION_POINTER_DOWN}.")
        const val ACTION_POINTER_1_DOWN = ACTION_POINTER_DOWN or 0x0000


        @Deprecated("Use {@link #ACTION_POINTER_INDEX_MASK} to retrieve the\n" +
                "      data index associated with {@link #ACTION_POINTER_DOWN}.")
        const val ACTION_POINTER_2_DOWN = ACTION_POINTER_DOWN or 0x0100


        @Deprecated("Use {@link #ACTION_POINTER_INDEX_MASK} to retrieve the\n" +
                "      data index associated with {@link #ACTION_POINTER_DOWN}.")
        const val ACTION_POINTER_3_DOWN = ACTION_POINTER_DOWN or 0x0200


        @Deprecated("Use {@link #ACTION_POINTER_INDEX_MASK} to retrieve the\n" +
                "      data index associated with {@link #ACTION_POINTER_UP}.")
        const val ACTION_POINTER_1_UP = ACTION_POINTER_UP or 0x0000


        @Deprecated("Use {@link #ACTION_POINTER_INDEX_MASK} to retrieve the\n" +
                "      data index associated with {@link #ACTION_POINTER_UP}.")
        const val ACTION_POINTER_2_UP = ACTION_POINTER_UP or 0x0100


        @Deprecated("Use {@link #ACTION_POINTER_INDEX_MASK} to retrieve the\n" +
                "      data index associated with {@link #ACTION_POINTER_UP}.")
        const val ACTION_POINTER_3_UP = ACTION_POINTER_UP or 0x0200


        @Deprecated("Renamed to {@link #ACTION_POINTER_INDEX_MASK} to match\n" +
                "      the actual data contained in these bits.")
        const val ACTION_POINTER_ID_MASK = 0xff00


        @Deprecated("Renamed to {@link #ACTION_POINTER_INDEX_SHIFT} to match\n" +
                "      the actual data contained in these bits.")
        const val ACTION_POINTER_ID_SHIFT = 8

        /**
         * This flag indicates that the window that received this motion event is partly
         * or wholly obscured by another visible window above it.  This flag is set to true
         * even if the event did not directly pass through the obscured area.
         * A security sensitive application can check this flag to identify situations in which
         * a malicious application may have covered up part of its content for the purpose
         * of misleading the user or hijacking touches.  An appropriate response might be
         * to drop the suspect touches or to take additional precautions to confirm the user's
         * actual intent.
         */
        const val FLAG_WINDOW_IS_OBSCURED = 0x1

        /**
         * This flag indicates that the window that received this motion event is partly
         * or wholly obscured by another visible window above it.  This flag is set to true
         * even if the event did not directly pass through the obscured area.
         * A security sensitive application can check this flag to identify situations in which
         * a malicious application may have covered up part of its content for the purpose
         * of misleading the user or hijacking touches.  An appropriate response might be
         * to drop the suspect touches or to take additional precautions to confirm the user's
         * actual intent.
         *
         * Unlike FLAG_WINDOW_IS_OBSCURED, this is actually true.
         * @hide
         */
        const val FLAG_WINDOW_IS_PARTIALLY_OBSCURED = 0x2

        /**
         * This private flag is only set on [.ACTION_HOVER_MOVE] events and indicates that
         * this event will be immediately followed by a [.ACTION_HOVER_EXIT]. It is used to
         * prevent generating redundant [.ACTION_HOVER_ENTER] events.
         * @hide
         */
        const val FLAG_HOVER_EXIT_PENDING = 0x4

        /**
         * This flag indicates that the event has been generated by a gesture generator. It
         * provides a hint to the GestureDector to not apply any touch slop.
         *
         * @hide
         */
        const val FLAG_IS_GENERATED_GESTURE = 0x8

        /**
         * Private flag that indicates when the system has detected that this motion event
         * may be inconsistent with respect to the sequence of previously delivered motion events,
         * such as when a pointer move event is sent but the pointer is not down.
         *
         * @hide
         * @see .isTainted
         *
         * @see .setTainted
         */
        const val FLAG_TAINTED = -0x80000000

        /**
         * Private flag indicating that this event was synthesized by the system and
         * should be delivered to the accessibility focused view first. When being
         * dispatched such an event is not handled by predecessors of the accessibility
         * focused view and after the event reaches that view the flag is cleared and
         * normal event dispatch is performed. This ensures that the platform can click
         * on any view that has accessibility focus which is semantically equivalent to
         * asking the view to perform a click accessibility action but more generic as
         * views not implementing click action correctly can still be activated.
         *
         * @hide
         * @see .isTargetAccessibilityFocus
         * @see .setTargetAccessibilityFocus
         */
        const val FLAG_TARGET_ACCESSIBILITY_FOCUS = 0x40000000


        /**
         * Flag indicating the motion event intersected the top edge of the screen.
         */
        const val EDGE_TOP = 0x00000001

        /**
         * Flag indicating the motion event intersected the bottom edge of the screen.
         */
        const val EDGE_BOTTOM = 0x00000002

        /**
         * Flag indicating the motion event intersected the left edge of the screen.
         */
        const val EDGE_LEFT = 0x00000004

        /**
         * Flag indicating the motion event intersected the right edge of the screen.
         */
        const val EDGE_RIGHT = 0x00000008

        /**
         * Axis constant: X axis of a motion event.
         *
         *
         *
         *  * For a touch screen, reports the absolute X screen position of the center of
         * the touch contact area.  The units are display pixels.
         *  * For a touch pad, reports the absolute X surface position of the center of the touch
         * contact area.  The units are device-dependent; use [InputDevice.getMotionRange]
         * to query the effective range of values.
         *  * For a mouse, reports the absolute X screen position of the mouse pointer.
         * The units are display pixels.
         *  * For a trackball, reports the relative horizontal displacement of the trackball.
         * The value is normalized to a range from -1.0 (left) to 1.0 (right).
         *  * For a joystick, reports the absolute X position of the joystick.
         * The value is normalized to a range from -1.0 (left) to 1.0 (right).
         *
         *
         *
         * @see .getX
         * @see .getHistoricalX
         * @see MotionEvent.PointerCoords.x
         *
         * @see InputDevice.getMotionRange
         */
        const val AXIS_X = 0

        /**
         * Axis constant: Y axis of a motion event.
         *
         *
         *
         *  * For a touch screen, reports the absolute Y screen position of the center of
         * the touch contact area.  The units are display pixels.
         *  * For a touch pad, reports the absolute Y surface position of the center of the touch
         * contact area.  The units are device-dependent; use [InputDevice.getMotionRange]
         * to query the effective range of values.
         *  * For a mouse, reports the absolute Y screen position of the mouse pointer.
         * The units are display pixels.
         *  * For a trackball, reports the relative vertical displacement of the trackball.
         * The value is normalized to a range from -1.0 (up) to 1.0 (down).
         *  * For a joystick, reports the absolute Y position of the joystick.
         * The value is normalized to a range from -1.0 (up or far) to 1.0 (down or near).
         *
         *
         *
         * @see .getY
         * @see .getHistoricalY
         * @see MotionEvent.PointerCoords.y
         *
         * @see InputDevice.getMotionRange
         */
        const val AXIS_Y = 1

        /**
         * Axis constant: Pressure axis of a motion event.
         *
         *
         *
         *  * For a touch screen or touch pad, reports the approximate pressure applied to the surface
         * by a finger or other tool.  The value is normalized to a range from
         * 0 (no pressure at all) to 1 (normal pressure), although values higher than 1
         * may be generated depending on the calibration of the input device.
         *  * For a trackball, the value is set to 1 if the trackball button is pressed
         * or 0 otherwise.
         *  * For a mouse, the value is set to 1 if the primary mouse button is pressed
         * or 0 otherwise.
         *
         *
         *
         * @see .getPressure
         * @see .getHistoricalPressure
         * @see MotionEvent.PointerCoords.pressure
         *
         * @see InputDevice.getMotionRange
         */
        const val AXIS_PRESSURE = 2

        /**
         * Axis constant: Size axis of a motion event.
         *
         *
         *
         *  * For a touch screen or touch pad, reports the approximate size of the contact area in
         * relation to the maximum detectable size for the device.  The value is normalized
         * to a range from 0 (smallest detectable size) to 1 (largest detectable size),
         * although it is not a linear scale.  This value is of limited use.
         * To obtain calibrated size information, use
         * [.AXIS_TOUCH_MAJOR] or [.AXIS_TOOL_MAJOR].
         *
         *
         *
         * @see .getSize
         * @see .getHistoricalSize
         * @see MotionEvent.PointerCoords.size
         *
         * @see InputDevice.getMotionRange
         */
        const val AXIS_SIZE = 3

        /**
         * Axis constant: TouchMajor axis of a motion event.
         *
         *
         *
         *  * For a touch screen, reports the length of the major axis of an ellipse that
         * represents the touch area at the point of contact.
         * The units are display pixels.
         *  * For a touch pad, reports the length of the major axis of an ellipse that
         * represents the touch area at the point of contact.
         * The units are device-dependent; use [InputDevice.getMotionRange]
         * to query the effective range of values.
         *
         *
         *
         * @see .getTouchMajor
         * @see .getHistoricalTouchMajor
         * @see MotionEvent.PointerCoords.touchMajor
         *
         * @see InputDevice.getMotionRange
         */
        const val AXIS_TOUCH_MAJOR = 4

        /**
         * Axis constant: TouchMinor axis of a motion event.
         *
         *
         *
         *  * For a touch screen, reports the length of the minor axis of an ellipse that
         * represents the touch area at the point of contact.
         * The units are display pixels.
         *  * For a touch pad, reports the length of the minor axis of an ellipse that
         * represents the touch area at the point of contact.
         * The units are device-dependent; use [InputDevice.getMotionRange]
         * to query the effective range of values.
         *
         *
         *
         * When the touch is circular, the major and minor axis lengths will be equal to one another.
         *
         *
         * @see .getTouchMinor
         * @see .getHistoricalTouchMinor
         * @see MotionEvent.PointerCoords.touchMinor
         *
         * @see InputDevice.getMotionRange
         */
        const val AXIS_TOUCH_MINOR = 5

        /**
         * Axis constant: ToolMajor axis of a motion event.
         *
         *
         *
         *  * For a touch screen, reports the length of the major axis of an ellipse that
         * represents the size of the approaching finger or tool used to make contact.
         *  * For a touch pad, reports the length of the major axis of an ellipse that
         * represents the size of the approaching finger or tool used to make contact.
         * The units are device-dependent; use [InputDevice.getMotionRange]
         * to query the effective range of values.
         *
         *
         *
         * When the touch is circular, the major and minor axis lengths will be equal to one another.
         *
         *
         * The tool size may be larger than the touch size since the tool may not be fully
         * in contact with the touch sensor.
         *
         *
         * @see .getToolMajor
         * @see .getHistoricalToolMajor
         * @see MotionEvent.PointerCoords.toolMajor
         *
         * @see InputDevice.getMotionRange
         */
        const val AXIS_TOOL_MAJOR = 6

        /**
         * Axis constant: ToolMinor axis of a motion event.
         *
         *
         *
         *  * For a touch screen, reports the length of the minor axis of an ellipse that
         * represents the size of the approaching finger or tool used to make contact.
         *  * For a touch pad, reports the length of the minor axis of an ellipse that
         * represents the size of the approaching finger or tool used to make contact.
         * The units are device-dependent; use [InputDevice.getMotionRange]
         * to query the effective range of values.
         *
         *
         *
         * When the touch is circular, the major and minor axis lengths will be equal to one another.
         *
         *
         * The tool size may be larger than the touch size since the tool may not be fully
         * in contact with the touch sensor.
         *
         *
         * @see .getToolMinor
         * @see .getHistoricalToolMinor
         * @see MotionEvent.PointerCoords.toolMinor
         *
         * @see InputDevice.getMotionRange
         */
        const val AXIS_TOOL_MINOR = 7

        /**
         * Axis constant: Orientation axis of a motion event.
         *
         *
         *
         *  * For a touch screen or touch pad, reports the orientation of the finger
         * or tool in radians relative to the vertical plane of the device.
         * An angle of 0 radians indicates that the major axis of contact is oriented
         * upwards, is perfectly circular or is of unknown orientation.  A positive angle
         * indicates that the major axis of contact is oriented to the right.  A negative angle
         * indicates that the major axis of contact is oriented to the left.
         * The full range is from -PI/2 radians (finger pointing fully left) to PI/2 radians
         * (finger pointing fully right).
         *  * For a stylus, the orientation indicates the direction in which the stylus
         * is pointing in relation to the vertical axis of the current orientation of the screen.
         * The range is from -PI radians to PI radians, where 0 is pointing up,
         * -PI/2 radians is pointing left, -PI or PI radians is pointing down, and PI/2 radians
         * is pointing right.  See also [.AXIS_TILT].
         *
         *
         *
         * @see .getOrientation
         * @see .getHistoricalOrientation
         * @see MotionEvent.PointerCoords.orientation
         *
         * @see InputDevice.getMotionRange
         */
        const val AXIS_ORIENTATION = 8

        /**
         * Axis constant: Vertical Scroll axis of a motion event.
         *
         *
         *
         *  * For a mouse, reports the relative movement of the vertical scroll wheel.
         * The value is normalized to a range from -1.0 (down) to 1.0 (up).
         *
         *
         *
         * This axis should be used to scroll views vertically.
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_VSCROLL = 9

        /**
         * Axis constant: Horizontal Scroll axis of a motion event.
         *
         *
         *
         *  * For a mouse, reports the relative movement of the horizontal scroll wheel.
         * The value is normalized to a range from -1.0 (left) to 1.0 (right).
         *
         *
         *
         * This axis should be used to scroll views horizontally.
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_HSCROLL = 10

        /**
         * Axis constant: Z axis of a motion event.
         *
         *
         *
         *  * For a joystick, reports the absolute Z position of the joystick.
         * The value is normalized to a range from -1.0 (high) to 1.0 (low).
         * *On game pads with two analog joysticks, this axis is often reinterpreted
         * to report the absolute X position of the second joystick instead.*
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_Z = 11

        /**
         * Axis constant: X Rotation axis of a motion event.
         *
         *
         *
         *  * For a joystick, reports the absolute rotation angle about the X axis.
         * The value is normalized to a range from -1.0 (counter-clockwise) to 1.0 (clockwise).
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_RX = 12

        /**
         * Axis constant: Y Rotation axis of a motion event.
         *
         *
         *
         *  * For a joystick, reports the absolute rotation angle about the Y axis.
         * The value is normalized to a range from -1.0 (counter-clockwise) to 1.0 (clockwise).
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_RY = 13

        /**
         * Axis constant: Z Rotation axis of a motion event.
         *
         *
         *
         *  * For a joystick, reports the absolute rotation angle about the Z axis.
         * The value is normalized to a range from -1.0 (counter-clockwise) to 1.0 (clockwise).
         * *On game pads with two analog joysticks, this axis is often reinterpreted
         * to report the absolute Y position of the second joystick instead.*
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_RZ = 14

        /**
         * Axis constant: Hat X axis of a motion event.
         *
         *
         *
         *  * For a joystick, reports the absolute X position of the directional hat control.
         * The value is normalized to a range from -1.0 (left) to 1.0 (right).
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_HAT_X = 15

        /**
         * Axis constant: Hat Y axis of a motion event.
         *
         *
         *
         *  * For a joystick, reports the absolute Y position of the directional hat control.
         * The value is normalized to a range from -1.0 (up) to 1.0 (down).
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_HAT_Y = 16

        /**
         * Axis constant: Left Trigger axis of a motion event.
         *
         *
         *
         *  * For a joystick, reports the absolute position of the left trigger control.
         * The value is normalized to a range from 0.0 (released) to 1.0 (fully pressed).
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_LTRIGGER = 17

        /**
         * Axis constant: Right Trigger axis of a motion event.
         *
         *
         *
         *  * For a joystick, reports the absolute position of the right trigger control.
         * The value is normalized to a range from 0.0 (released) to 1.0 (fully pressed).
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_RTRIGGER = 18

        /**
         * Axis constant: Throttle axis of a motion event.
         *
         *
         *
         *  * For a joystick, reports the absolute position of the throttle control.
         * The value is normalized to a range from 0.0 (fully open) to 1.0 (fully closed).
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_THROTTLE = 19

        /**
         * Axis constant: Rudder axis of a motion event.
         *
         *
         *
         *  * For a joystick, reports the absolute position of the rudder control.
         * The value is normalized to a range from -1.0 (turn left) to 1.0 (turn right).
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_RUDDER = 20

        /**
         * Axis constant: Wheel axis of a motion event.
         *
         *
         *
         *  * For a joystick, reports the absolute position of the steering wheel control.
         * The value is normalized to a range from -1.0 (turn left) to 1.0 (turn right).
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_WHEEL = 21

        /**
         * Axis constant: Gas axis of a motion event.
         *
         *
         *
         *  * For a joystick, reports the absolute position of the gas (accelerator) control.
         * The value is normalized to a range from 0.0 (no acceleration)
         * to 1.0 (maximum acceleration).
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_GAS = 22

        /**
         * Axis constant: Brake axis of a motion event.
         *
         *
         *
         *  * For a joystick, reports the absolute position of the brake control.
         * The value is normalized to a range from 0.0 (no braking) to 1.0 (maximum braking).
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_BRAKE = 23

        /**
         * Axis constant: Distance axis of a motion event.
         *
         *
         *
         *  * For a stylus, reports the distance of the stylus from the screen.
         * A value of 0.0 indicates direct contact and larger values indicate increasing
         * distance from the surface.
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_DISTANCE = 24

        /**
         * Axis constant: Tilt axis of a motion event.
         *
         *
         *
         *  * For a stylus, reports the tilt angle of the stylus in radians where
         * 0 radians indicates that the stylus is being held perpendicular to the
         * surface, and PI/2 radians indicates that the stylus is being held flat
         * against the surface.
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_TILT = 25

        /**
         * Axis constant: Generic scroll axis of a motion event.
         *
         *
         *
         *  * Reports the relative movement of the generic scrolling device.
         *
         *
         *
         * This axis should be used for scroll events that are neither strictly vertical nor horizontal.
         * A good example would be the rotation of a rotary encoder input device.
         *
         *
         * @see .getAxisValue
         */
        const val AXIS_SCROLL = 26

        /**
         * Axis constant: The movement of x position of a motion event.
         *
         *
         *
         *  * For a mouse, reports a difference of x position between the previous position.
         * This is useful when pointer is captured, in that case the mouse pointer doesn't change
         * the location but this axis reports the difference which allows the app to see
         * how the mouse is moved.
         *
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_RELATIVE_X = 27

        /**
         * Axis constant: The movement of y position of a motion event.
         *
         *
         * This is similar to [.AXIS_RELATIVE_X] but for y-axis.
         *
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_RELATIVE_Y = 28

        /**
         * Axis constant: Generic 1 axis of a motion event.
         * The interpretation of a generic axis is device-specific.
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_GENERIC_1 = 32

        /**
         * Axis constant: Generic 2 axis of a motion event.
         * The interpretation of a generic axis is device-specific.
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_GENERIC_2 = 33

        /**
         * Axis constant: Generic 3 axis of a motion event.
         * The interpretation of a generic axis is device-specific.
         *
         * @see .getAxisValue
         * @see .getHistoricalAxisValue
         * @see MotionEvent.PointerCoords.getAxisValue
         * @see InputDevice.getMotionRange
         */
        const val AXIS_GENERIC_3 = 34




    }

}