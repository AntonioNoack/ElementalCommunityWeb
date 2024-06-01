package android.media

import android.content.Context
import org.w3c.dom.Audio

class MediaPlayer(val src: String) {

    val instance = Audio()
    var isLoaded = false

    init {
        instance.addEventListener("error", {
            println("Media $src error")
        })
        instance.addEventListener("canplaythrough", {
            isLoaded = true
        })
        instance.src = src
    }

    fun start() {
        if (isLoaded) {
            try {
                instance.currentTime = 0.0
                instance.play()
            } catch (e: Throwable) {
                println(e)
            }
        } else console.log(src, "hasn't been loaded yet")
    }

    fun seekTo(seconds: Int) {
        instance.currentTime = seconds.toDouble()
    }

    fun stop() {
        // not really needed...
        instance.pause()
    }

    fun pause() {
        instance.pause()
    }

    fun setVolume(left: Float, right: Float) {
        instance.volume = left.toDouble()
    }

    val currentPosition get() = (instance.currentTime * 1000).toInt()
    val isPlaying get() = !instance.paused

    companion object {
        fun create(ctx: Context, src: String) = MediaPlayer(src)
    }
}