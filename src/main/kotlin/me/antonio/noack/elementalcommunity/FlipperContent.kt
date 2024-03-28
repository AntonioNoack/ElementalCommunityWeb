package me.antonio.noack.elementalcommunity

import android.view.View

enum class FlipperContent(val id: String) {

    MENU(R.id.menuLayout),
    GAME(R.id.gameLayout),
    MANDALA(R.id.mandalaLayout),
    TREE(R.id.treeLayout),
    GRAPH(R.id.graphLayout),
    COMBINER(R.id.combinerLayout),
    ITEMPEDIA(R.id.itempedia),
    SETTINGS(R.id.settingsLayout),

    ;

    fun bind(all: AllManager) {
        val flipper = all.flipper ?: return
        val v = flipper.findViewById<View>(id)
        val index = if (v != null) {
            (0 until flipper.childCount).indexOfFirst {
                flipper.getChildAt(it) == v
            }
        } else 0
        /*if (index > 0) {
            flipper.setInAnimation(all, R.anim.slide_in_right)
            flipper.setOutAnimation(all, R.anim.slide_out_right)
        } else {
            flipper.setInAnimation(all, R.anim.slide_in_left)
            flipper.setOutAnimation(all, R.anim.slide_out_left)
        }*/
        flipper.displayedChild = index
    }

}