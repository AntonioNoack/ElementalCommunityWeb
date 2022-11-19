package android.view

class LayoutInflater {

    fun inflate(id: View, parent: View, append: Boolean): View {

        val view = id.clone()
        if(append){
            parent.addChild(view)
        }

        return view

    }

    fun inflate(id: View, append: Boolean, parent: View){

        val view = id.clone()
        if(append){
            parent.addChild(view)
        }
    }

    companion object {
        val instance = LayoutInflater()
    }
}