package me.antonio.noack.webdroid

import org.w3c.xhr.XMLHttpRequest
import java.io.IOException

object HTTP {

    fun request(url: String, onSuccess: (String) -> Unit, onError: (IOException) -> Unit){

        var sentResponse = false
        val x = XMLHttpRequest()
        x.open("GET", url)
        x.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
        x.onreadystatechange = {
            if(x.readyState == 4.toShort()){
                if(x.status == 200.toShort()){
                    if(!sentResponse){
                        sentResponse = true
                        onSuccess(x.responseText)
                    }
                } else onError(IOException("${x.status}"))
            }

        }
        x.send()

    }

    fun requestLarge(url: String, largeArgs: String, onSuccess: (String) -> Unit, onError: (IOException) -> Unit, https: Boolean){

        var sentResponse = false
        val x = XMLHttpRequest()
        x.open("POST", url)
        x.setRequestHeader("Content-type", "application/x-www-form-urlencoded")
        x.onreadystatechange = {
            if(x.readyState == 4.toShort()){
                if(x.status == 200.toShort()){
                    if(!sentResponse){
                        sentResponse = true
                        onSuccess(x.responseText)
                    }
                } else onError(IOException("${x.status}"))
            }

        }
        x.send(largeArgs)

    }

}