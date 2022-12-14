package androidx.appcompat.app

import android.app.ActionBar
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.Window
import me.antonio.noack.webdroid.Runner

open class AppCompatActivity: Context(){

    open fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){}

    open fun onCreate(savedInstanceState: Bundle?){

    }

    open fun onBackPressed(){}
    open fun onDestroy(){}
    open fun onResume() {}

    val actionBar: ActionBar? = null

    fun setContentView(layout: View){
        children.clear()
        children.add(layout)
        layout.mParent = this
        layout.init()
    }

    val window = Window()

}