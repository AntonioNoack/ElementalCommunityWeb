package android.content

import android.content.res.Resources
import android.os.Vibrator
import android.view.LayoutInflater
import android.widget.FrameLayout

open class Context: FrameLayout(null, null){

    val resources = Resources()
    val layoutInflater = LayoutInflater.instance

    fun getPreferences(mode: Int) = LocalStoragePreferences()

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){

    }

    fun getSystemService(key: String): Any? {
        when(key){
            VIBRATOR_SERVICE -> return Vibrator()
        }
        println("Warning: Service $key not found")
        return null
    }

    companion object {
        const val MODE_PRIVATE = 1
        const val VIBRATOR_SERVICE = "VIBRATOR_SERVICE"
    }

}