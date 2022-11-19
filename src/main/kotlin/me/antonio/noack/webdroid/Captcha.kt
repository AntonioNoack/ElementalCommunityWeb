package me.antonio.noack.webdroid

// import com.google.android.gms.safetynet.SafetyNet
import me.antonio.noack.elementalcommunity.AllManager
import org.w3c.dom.HTMLScriptElement
import java.lang.Exception
import kotlinx.browser.document

external fun askCaptcha(callback: (token: String) -> Unit, onError: () -> Unit)

object Captcha {

    private var isInitialized = false
    fun init(){
        if(isInitialized) return
        val child = document.createElement("script") as HTMLScriptElement
        child.src = "https://www.google.com/recaptcha/api.js"
        document.body!!.appendChild(child)
        isInitialized = true
    }

    fun get(all: AllManager, onSuccess: (String) -> Unit, onError: (Exception) -> Unit){

        askCaptcha(onSuccess) {
            onError(Exception())
        }

    }

}