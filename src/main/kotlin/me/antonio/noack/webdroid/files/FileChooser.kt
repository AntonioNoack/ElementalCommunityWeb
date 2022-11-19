package me.antonio.noack.webdroid.files

import me.antonio.noack.elementalcommunity.AllManager

object FileChooser {
    fun requestFile(all: AllManager, mimeType: String, onSuccess: (String) -> Unit) {
        askForFile(onSuccess)
    }
}

external fun askForFile(callback: (String) -> Unit)