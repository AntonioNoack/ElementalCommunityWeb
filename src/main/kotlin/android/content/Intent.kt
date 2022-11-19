package android.content

class Intent(val requestType: Int){

    var type = ""

    fun addCategory(cat: Any){}

    companion object {

        fun createChooser(target: Intent, title: String): Intent = target

        val CATEGORY_OPENABLE = 0
        val ACTION_GET_CONTENT = 1


    }

}