package me.antonio.noack.elementalcommunity

enum class FlipperContent(val id: Int) {

    MENU(0),
    GAME(1),
    MANDALA(2),
    TREE(3),
    GRAPH(4),
    COMBINER(5),
    ITEMPEDIA(6),
    SETTINGS(7);

    fun bind(all: AllManager) {
        all.flipper?.displayedChild = id
    }
}