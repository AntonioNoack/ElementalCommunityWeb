package android.view

class Window {
    var navigationBarColor = 0
    fun clearFlags(args: Int){}
    fun setSoftInputMode(args: Int){}
    companion object {
        val instance = Window()
    }
}