package android.media

import android.content.Context
import org.w3c.dom.Audio

class MediaPlayer(val src: String){

    val instance = Audio()
    var shallPlay = false
    var isLoaded = false
    init {
        instance.onerror = { _, _, _, _, _ ->
            println("Media $src error")
        }
        instance.src = src
    }

    fun start(){
        try {
            instance.currentTime = 0.0
            instance.play()
        } catch (e: Throwable){
            println(e)
        }
    }

    fun seekTo(seconds: Int){
        instance.currentTime = seconds.toDouble()
    }

    fun stop(){
        // not really needed...
        instance.pause()
    }

    companion object {
        fun create(ctx: Context, src: String) = MediaPlayer(src)
    }
}