package me.antonio.noack.webdroid.files

import me.antonio.noack.elementalcommunity.AllManager

external fun download(fileName: String, mimeType: String, content: String)

object FileSaver {

    fun save(all: AllManager, fileName: String, mimeType: String, content: String){
        download(fileName, mimeType, content)
    }

}