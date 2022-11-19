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

package android.view

import android.graphics.Rect

/**
 * Standard constants and tools for placing an object within a potentially
 * larger container.
 */
object Gravity {

    /** Constant indicating that no gravity has been set  */
    val NO_GRAVITY = 0x0000

    /** Raw bit indicating the gravity for an axis has been specified.  */
    val AXIS_SPECIFIED = 0x0001

    /** Raw bit controlling how the left/top edge is placed.  */
    val AXIS_PULL_BEFORE = 0x0002
    /** Raw bit controlling how the right/bottom edge is placed.  */
    val AXIS_PULL_AFTER = 0x0004
    /** Raw bit controlling whether the right/bottom edge is clipped to its
     * container, based on the gravity direction being applied.  */
    val AXIS_CLIP = 0x0008

    /** Bits defining the horizontal axis.  */
    val AXIS_X_SHIFT = 0
    /** Bits defining the vertical axis.  */
    val AXIS_Y_SHIFT = 4

    /** Push object to the top of its container, not changing its size.  */
    val TOP = AXIS_PULL_BEFORE or AXIS_SPECIFIED shl AXIS_Y_SHIFT
    /** Push object to the bottom of its container, not changing its size.  */
    val BOTTOM = AXIS_PULL_AFTER or AXIS_SPECIFIED shl AXIS_Y_SHIFT
    /** Push object to the left of its container, not changing its size.  */
    val LEFT = AXIS_PULL_BEFORE or AXIS_SPECIFIED shl AXIS_X_SHIFT
    /** Push object to the right of its container, not changing its size.  */
    val RIGHT = AXIS_PULL_AFTER or AXIS_SPECIFIED shl AXIS_X_SHIFT

    /** Place object in the vertical center of its container, not changing its
     * size.  */
    val CENTER_VERTICAL = AXIS_SPECIFIED shl AXIS_Y_SHIFT
    /** Grow the vertical size of the object if needed so it completely fills
     * its container.  */
    val FILL_VERTICAL = TOP or BOTTOM

    /** Place object in the horizontal center of its container, not changing its
     * size.  */
    val CENTER_HORIZONTAL = AXIS_SPECIFIED shl AXIS_X_SHIFT
    /** Grow the horizontal size of the object if needed so it completely fills
     * its container.  */
    val FILL_HORIZONTAL = LEFT or RIGHT

    /** Place the object in the center of its container in both the vertical
     * and horizontal axis, not changing its size.  */
    val CENTER = CENTER_VERTICAL or CENTER_HORIZONTAL

    /** Grow the horizontal and vertical size of the object if needed so it
     * completely fills its container.  */
    val FILL = FILL_VERTICAL or FILL_HORIZONTAL

    /** Flag to clip the edges of the object to its container along the
     * vertical axis.  */
    val CLIP_VERTICAL = AXIS_CLIP shl AXIS_Y_SHIFT

    /** Flag to clip the edges of the object to its container along the
     * horizontal axis.  */
    val CLIP_HORIZONTAL = AXIS_CLIP shl AXIS_X_SHIFT

    /** Raw bit controlling whether the layout direction is relative or not (START/END instead of
     * absolute LEFT/RIGHT).
     */
    val RELATIVE_LAYOUT_DIRECTION = 0x00800000

    /**
     * Binary mask to get the absolute horizontal gravity of a gravity.
     */
    val HORIZONTAL_GRAVITY_MASK = AXIS_SPECIFIED or
            AXIS_PULL_BEFORE or AXIS_PULL_AFTER shl AXIS_X_SHIFT
    /**
     * Binary mask to get the vertical gravity of a gravity.
     */
    val VERTICAL_GRAVITY_MASK = AXIS_SPECIFIED or
            AXIS_PULL_BEFORE or AXIS_PULL_AFTER shl AXIS_Y_SHIFT

    /** Special constant to enable clipping to an overall display along the
     * vertical dimension.  This is not applied by default by
     * [.apply]; you must do so
     * yourself by calling [.applyDisplay].
     */
    val DISPLAY_CLIP_VERTICAL = 0x10000000

    /** Special constant to enable clipping to an overall display along the
     * horizontal dimension.  This is not applied by default by
     * [.apply]; you must do so
     * yourself by calling [.applyDisplay].
     */
    val DISPLAY_CLIP_HORIZONTAL = 0x01000000

    /** Push object to x-axis position at the start of its container, not changing its size.  */
    val START = RELATIVE_LAYOUT_DIRECTION or LEFT

    /** Push object to x-axis position at the end of its container, not changing its size.  */
    val END = RELATIVE_LAYOUT_DIRECTION or RIGHT

    /**
     * Binary mask for the horizontal gravity and script specific direction bit.
     */
    val RELATIVE_HORIZONTAL_GRAVITY_MASK = START or END

    /**
     * Apply a gravity constant to an object. This supposes that the layout direction is LTR.
     *
     * @param gravity The desired placement of the object, as defined by the
     * constants in this class.
     * @param w The horizontal size of the object.
     * @param h The vertical size of the object.
     * @param container The frame of the containing space, in which the object
     * will be placed.  Should be large enough to contain the
     * width and height of the object.
     * @param outRect Receives the computed frame of the object in its
     * container.
     */
    fun apply(gravity: Int, w: Int, h: Int, container: Rect, outRect: Rect) {
        apply(gravity, w, h, container, 0, 0, outRect)
    }

    /**
     * Apply a gravity constant to an object and take care if layout direction is RTL or not.
     *
     * @param gravity The desired placement of the object, as defined by the
     * constants in this class.
     * @param w The horizontal size of the object.
     * @param h The vertical size of the object.
     * @param container The frame of the containing space, in which the object
     * will be placed.  Should be large enough to contain the
     * width and height of the object.
     * @param outRect Receives the computed frame of the object in its
     * container.
     * @param layoutDirection The layout direction.
     *
     * @see View.LAYOUT_DIRECTION_LTR
     *
     * @see View.LAYOUT_DIRECTION_RTL
     */
    fun apply(gravity: Int, w: Int, h: Int, container: Rect,
              outRect: Rect, layoutDirection: Int) {
        val absGravity = getAbsoluteGravity(gravity, layoutDirection)
        apply(absGravity, w, h, container, 0, 0, outRect)
    }

    /**
     * Apply a gravity constant to an object.
     *
     * @param gravity The desired placement of the object, as defined by the
     * constants in this class.
     * @param w The horizontal size of the object.
     * @param h The vertical size of the object.
     * @param container The frame of the containing space, in which the object
     * will be placed.  Should be large enough to contain the
     * width and height of the object.
     * @param xAdj Offset to apply to the X axis.  If gravity is LEFT this
     * pushes it to the right; if gravity is RIGHT it pushes it to
     * the left; if gravity is CENTER_HORIZONTAL it pushes it to the
     * right or left; otherwise it is ignored.
     * @param yAdj Offset to apply to the Y axis.  If gravity is TOP this pushes
     * it down; if gravity is BOTTOM it pushes it up; if gravity is
     * CENTER_VERTICAL it pushes it down or up; otherwise it is
     * ignored.
     * @param outRect Receives the computed frame of the object in its
     * container.
     */
    fun apply(gravity: Int, w: Int, h: Int, container: Rect,
              xAdj: Int, yAdj: Int, outRect: Rect) {
        when (gravity and (AXIS_PULL_BEFORE or AXIS_PULL_AFTER shl AXIS_X_SHIFT)) {
            0 -> {
                outRect.left = (container.left
                        + (container.right - container.left - w) / 2 + xAdj)
                outRect.right = outRect.left + w
                if (gravity and (AXIS_CLIP shl AXIS_X_SHIFT) == AXIS_CLIP shl AXIS_X_SHIFT) {
                    if (outRect.left < container.left) {
                        outRect.left = container.left
                    }
                    if (outRect.right > container.right) {
                        outRect.right = container.right
                    }
                }
            }
            AXIS_PULL_BEFORE shl AXIS_X_SHIFT -> {
                outRect.left = container.left + xAdj
                outRect.right = outRect.left + w
                if (gravity and (AXIS_CLIP shl AXIS_X_SHIFT) == AXIS_CLIP shl AXIS_X_SHIFT) {
                    if (outRect.right > container.right) {
                        outRect.right = container.right
                    }
                }
            }
            AXIS_PULL_AFTER shl AXIS_X_SHIFT -> {
                outRect.right = container.right - xAdj
                outRect.left = outRect.right - w
                if (gravity and (AXIS_CLIP shl AXIS_X_SHIFT) == AXIS_CLIP shl AXIS_X_SHIFT) {
                    if (outRect.left < container.left) {
                        outRect.left = container.left
                    }
                }
            }
            else -> {
                outRect.left = container.left + xAdj
                outRect.right = container.right + xAdj
            }
        }

        when (gravity and (AXIS_PULL_BEFORE or AXIS_PULL_AFTER shl AXIS_Y_SHIFT)) {
            0 -> {
                outRect.top = (container.top
                        + (container.bottom - container.top - h) / 2 + yAdj)
                outRect.bottom = outRect.top + h
                if (gravity and (AXIS_CLIP shl AXIS_Y_SHIFT) == AXIS_CLIP shl AXIS_Y_SHIFT) {
                    if (outRect.top < container.top) {
                        outRect.top = container.top
                    }
                    if (outRect.bottom > container.bottom) {
                        outRect.bottom = container.bottom
                    }
                }
            }
            AXIS_PULL_BEFORE shl AXIS_Y_SHIFT -> {
                outRect.top = container.top + yAdj
                outRect.bottom = outRect.top + h
                if (gravity and (AXIS_CLIP shl AXIS_Y_SHIFT) == AXIS_CLIP shl AXIS_Y_SHIFT) {
                    if (outRect.bottom > container.bottom) {
                        outRect.bottom = container.bottom
                    }
                }
            }
            AXIS_PULL_AFTER shl AXIS_Y_SHIFT -> {
                outRect.bottom = container.bottom - yAdj
                outRect.top = outRect.bottom - h
                if (gravity and (AXIS_CLIP shl AXIS_Y_SHIFT) == AXIS_CLIP shl AXIS_Y_SHIFT) {
                    if (outRect.top < container.top) {
                        outRect.top = container.top
                    }
                }
            }
            else -> {
                outRect.top = container.top + yAdj
                outRect.bottom = container.bottom + yAdj
            }
        }
    }

    /**
     * Apply a gravity constant to an object.
     *
     * @param gravity The desired placement of the object, as defined by the
     * constants in this class.
     * @param w The horizontal size of the object.
     * @param h The vertical size of the object.
     * @param container The frame of the containing space, in which the object
     * will be placed.  Should be large enough to contain the
     * width and height of the object.
     * @param xAdj Offset to apply to the X axis.  If gravity is LEFT this
     * pushes it to the right; if gravity is RIGHT it pushes it to
     * the left; if gravity is CENTER_HORIZONTAL it pushes it to the
     * right or left; otherwise it is ignored.
     * @param yAdj Offset to apply to the Y axis.  If gravity is TOP this pushes
     * it down; if gravity is BOTTOM it pushes it up; if gravity is
     * CENTER_VERTICAL it pushes it down or up; otherwise it is
     * ignored.
     * @param outRect Receives the computed frame of the object in its
     * container.
     * @param layoutDirection The layout direction.
     *
     * @see View.LAYOUT_DIRECTION_LTR
     *
     * @see View.LAYOUT_DIRECTION_RTL
     */
    fun apply(gravity: Int, w: Int, h: Int, container: Rect,
              xAdj: Int, yAdj: Int, outRect: Rect, layoutDirection: Int) {
        val absGravity = getAbsoluteGravity(gravity, layoutDirection)
        apply(absGravity, w, h, container, xAdj, yAdj, outRect)
    }

    /**
     * Apply additional gravity behavior based on the overall "display" that an
     * object exists in.  This can be used after
     * [.apply] to place the object
     * within a visible display.  By default this moves or clips the object
     * to be visible in the display; the gravity flags
     * [.DISPLAY_CLIP_HORIZONTAL] and [.DISPLAY_CLIP_VERTICAL]
     * can be used to change this behavior.
     *
     * @param gravity Gravity constants to modify the placement within the
     * display.
     * @param display The rectangle of the display in which the object is
     * being placed.
     * @param inoutObj Supplies the current object position; returns with it
     * modified if needed to fit in the display.
     */
    fun applyDisplay(gravity: Int, display: Rect, inoutObj: Rect) {
        if (gravity and DISPLAY_CLIP_VERTICAL != 0) {
            if (inoutObj.top < display.top) inoutObj.top = display.top
            if (inoutObj.bottom > display.bottom) inoutObj.bottom = display.bottom
        } else {
            var off = 0
            if (inoutObj.top < display.top)
                off = display.top - inoutObj.top
            else if (inoutObj.bottom > display.bottom) off = display.bottom - inoutObj.bottom
            if (off != 0) {
                if (inoutObj.height() > display.bottom - display.top) {
                    inoutObj.top = display.top
                    inoutObj.bottom = display.bottom
                } else {
                    inoutObj.top += off
                    inoutObj.bottom += off
                }
            }
        }

        if (gravity and DISPLAY_CLIP_HORIZONTAL != 0) {
            if (inoutObj.left < display.left) inoutObj.left = display.left
            if (inoutObj.right > display.right) inoutObj.right = display.right
        } else {
            var off = 0
            if (inoutObj.left < display.left)
                off = display.left - inoutObj.left
            else if (inoutObj.right > display.right) off = display.right - inoutObj.right
            if (off != 0) {
                if (inoutObj.width() > display.right - display.left) {
                    inoutObj.left = display.left
                    inoutObj.right = display.right
                } else {
                    inoutObj.left += off
                    inoutObj.right += off
                }
            }
        }
    }

    /**
     * Apply additional gravity behavior based on the overall "display" that an
     * object exists in.  This can be used after
     * [.apply] to place the object
     * within a visible display.  By default this moves or clips the object
     * to be visible in the display; the gravity flags
     * [.DISPLAY_CLIP_HORIZONTAL] and [.DISPLAY_CLIP_VERTICAL]
     * can be used to change this behavior.
     *
     * @param gravity Gravity constants to modify the placement within the
     * display.
     * @param display The rectangle of the display in which the object is
     * being placed.
     * @param inoutObj Supplies the current object position; returns with it
     * modified if needed to fit in the display.
     * @param layoutDirection The layout direction.
     *
     * @see View.LAYOUT_DIRECTION_LTR
     *
     * @see View.LAYOUT_DIRECTION_RTL
     */
    fun applyDisplay(gravity: Int, display: Rect, inoutObj: Rect, layoutDirection: Int) {
        val absGravity = getAbsoluteGravity(gravity, layoutDirection)
        applyDisplay(absGravity, display, inoutObj)
    }

    /**
     *
     * Indicate whether the supplied gravity has a vertical pull.
     *
     * @param gravity the gravity to check for vertical pull
     * @return true if the supplied gravity has a vertical pull
     */
    fun isVertical(gravity: Int): Boolean {
        return gravity > 0 && gravity and VERTICAL_GRAVITY_MASK != 0
    }

    /**
     *
     * Indicate whether the supplied gravity has an horizontal pull.
     *
     * @param gravity the gravity to check for horizontal pull
     * @return true if the supplied gravity has an horizontal pull
     */
    fun isHorizontal(gravity: Int): Boolean {
        return gravity > 0 && gravity and RELATIVE_HORIZONTAL_GRAVITY_MASK != 0
    }

    /**
     *
     * Convert script specific gravity to absolute horizontal value.
     *
     * if horizontal direction is LTR, then START will set LEFT and END will set RIGHT.
     * if horizontal direction is RTL, then START will set RIGHT and END will set LEFT.
     *
     *
     * @param gravity The gravity to convert to absolute (horizontal) values.
     * @param layoutDirection The layout direction.
     * @return gravity converted to absolute (horizontal) values.
     */
    fun getAbsoluteGravity(gravity: Int, layoutDirection: Int): Int {
        var result = gravity
        // If layout is script specific and gravity is horizontal relative (START or END)
        if (result and RELATIVE_LAYOUT_DIRECTION > 0) {
            if (result and Gravity.START == Gravity.START) {
                // Remove the START bit
                result = result and START.inv()
                if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                    // Set the RIGHT bit
                    result = result or RIGHT
                } else {
                    // Set the LEFT bit
                    result = result or LEFT
                }
            } else if (result and Gravity.END == Gravity.END) {
                // Remove the END bit
                result = result and END.inv()
                if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                    // Set the LEFT bit
                    result = result or LEFT
                } else {
                    // Set the RIGHT bit
                    result = result or RIGHT
                }
            }
            // Don't need the script specific bit any more, so remove it as we are converting to
            // absolute values (LEFT or RIGHT)
            result = result and RELATIVE_LAYOUT_DIRECTION.inv()
        }
        return result
    }

    /**
     * @hide
     */
    fun toString(gravity: Int): String {

        val result = StringBuilder()
        if (gravity and FILL == FILL) {
            result.append("FILL").append(' ')
        } else {
            if (gravity and FILL_VERTICAL == FILL_VERTICAL) {
                result.append("FILL_VERTICAL").append(' ')
            } else {
                if (gravity and TOP == TOP) {
                    result.append("TOP").append(' ')
                }
                if (gravity and BOTTOM == BOTTOM) {
                    result.append("BOTTOM").append(' ')
                }
            }
            if (gravity and FILL_HORIZONTAL == FILL_HORIZONTAL) {
                result.append("FILL_HORIZONTAL").append(' ')
            } else {
                if (gravity and START == START) {
                    result.append("START").append(' ')
                } else if (gravity and LEFT == LEFT) {
                    result.append("LEFT").append(' ')
                }
                if (gravity and END == END) {
                    result.append("END").append(' ')
                } else if (gravity and RIGHT == RIGHT) {
                    result.append("RIGHT").append(' ')
                }
            }
        }
        if (gravity and CENTER == CENTER) {
            result.append("CENTER").append(' ')
        } else {
            if (gravity and CENTER_VERTICAL == CENTER_VERTICAL) {
                result.append("CENTER_VERTICAL").append(' ')
            }
            if (gravity and CENTER_HORIZONTAL == CENTER_HORIZONTAL) {
                result.append("CENTER_HORIZONTAL").append(' ')
            }
        }
        if (result.isEmpty()) {
            result.append("NO GRAVITY").append(' ')
        }
        if (gravity and DISPLAY_CLIP_VERTICAL == DISPLAY_CLIP_VERTICAL) {
            result.append("DISPLAY_CLIP_VERTICAL").append(' ')
        }
        if (gravity and DISPLAY_CLIP_HORIZONTAL == DISPLAY_CLIP_HORIZONTAL) {
            result.append("DISPLAY_CLIP_HORIZONTAL").append(' ')
        }

        return result.toString().substring(0, result.lastIndex)
    }

    fun parseGravity(str: String): Int {
        return when(str.toUpperCase()){
            "TOP" -> TOP
            "BOTTOM" -> BOTTOM
            "FILL" -> FILL
            "LEFT" -> LEFT
            "RIGHT" -> RIGHT
            "CENTER" -> CENTER
            "CENTER_VERTICAL" -> CENTER_VERTICAL
            "CENTER_HORIZONTAL" -> CENTER_HORIZONTAL
            else -> NO_GRAVITY
        }
    }
}
