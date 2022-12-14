package me.antonio.noack.elementalcommunity

import androidx.core.math.MathUtils.clamp
import me.antonio.noack.elementalcommunity.AllManager.Companion.edit
import me.antonio.noack.elementalcommunity.AllManager.Companion.elementById
import me.antonio.noack.elementalcommunity.AllManager.Companion.elementByName
import me.antonio.noack.elementalcommunity.AllManager.Companion.elementsByGroup
import me.antonio.noack.elementalcommunity.AllManager.Companion.invalidate
import me.antonio.noack.elementalcommunity.AllManager.Companion.saveElement
import me.antonio.noack.elementalcommunity.AllManager.Companion.saveElement2
import me.antonio.noack.elementalcommunity.AllManager.Companion.unlockeds
import me.antonio.noack.elementalcommunity.GroupsEtc.minimumCraftingCount
import kotlin.math.min

class Element private constructor(var name: String, val uuid: Int, var group: Int): Comparable<Element> {

    override fun compareTo(other: Element): Int {
        val y = startingNumber.compareTo(other.startingNumber)
        if(y != 0) return y
        val x = hashLong.compareTo(other.hashLong)
        if(x != 0) return x
        else return lcName.compareTo(other.lcName)
    }

    var rank = -1

    /**
     * how often it was created, roughly
     * */
    var craftingCount = -1

    var srcA: Element? = null
    var srcB: Element? = null
    var hasTreeOutput = false

    var treeX = 0
    var treeY = 0

    var lcName = name.toLowerCase()
    var hashLong = calcHashLong()
    var startingNumber = calcStartingNumber()

    fun calcStartingNumber(): Long {

        var num = 0L
        var i = 0

        val isNegative = if(lcName.startsWith("-")){
            i++
            true
        } else {
            false
        }

        val limit = if(isNegative) 20 else 19

        number@ while(i < lcName.length){
            if(i == limit) return if(isNegative) Long.MIN_VALUE else Long.MAX_VALUE
            when(lcName[i]){
                in '0' .. '9' -> {
                    num = num * 10 + lcName[i].toInt() - '0'.toInt()
                }
                else -> break@number
            }
            i++
        }

        return if(isNegative) -num else num
    }

    fun calcHashLong(): Long {
        val lcName = lcName
        var x = 0L
        for(i in 0 until min(9, lcName.length)){
            x = x.shl(7) or lcName[i].toLong()
        }
        for(i in min(9, lcName.length) until 9){
            x = x.shl(7)
        }
        return x
    }

    init {
        if(null != Unit){
            elementById.put(uuid, this)
            elementByName[name] = this
            elementsByGroup[group].add(this)
        }
    }

    override fun equals(other: Any?): Boolean {
        return uuid == (other as? Element)?.uuid
    }

    override fun hashCode(): Int {
        return group * 12461 + uuid
    }

    override fun toString(): String {
        return "$name($uuid, $group)"
    }

    companion object {
        fun get(name: String, uuid: Int, group: Int, craftingCount: Int): Element {
            val old = elementById[uuid]
            return if(old != null){
                var needsSave = old.name != name
                if(needsSave){
                    old.name = name
                    old.lcName = name.toLowerCase()
                    old.startingNumber = old.calcStartingNumber()
                    old.hashLong = old.calcHashLong()
                }
                if(craftingCount >= minimumCraftingCount){
                    old.craftingCount = craftingCount
                    needsSave = true
                }
                if(old.group != group){
                    if(null != Unit){
                        // println("group for $name: $group")
                        val newGroup = clamp(group, 0, elementsByGroup.size-1)
                        elementsByGroup[old.group].remove(old)
                        unlockeds[old.group].remove(old)
                        elementsByGroup[newGroup].add(old)
                        unlockeds[newGroup].add(old)
                        old.group = newGroup
                        // there is the issue:
                        // saving is done inefficiently
                        needsSave = true
                    }
                    invalidate()
                }
                if(needsSave){
                    saveElement2(old)
                }
                old
            } else Element(name, uuid, group)
        }
    }

}