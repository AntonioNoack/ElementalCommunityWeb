package android.view

object LayoutInflater {

    fun inflate(id: View, parent: View, append: Boolean = true): View {
        val view = id.clone()
        if (append) {
            parent.addChild(view)
        }
        return view
    }

    fun inflate(id: View, append: Boolean, parent: View) {
        val view = id.clone()
        if (append) {
            parent.addChild(view)
        }
    }
}