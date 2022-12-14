package me.antonio.noack.elementalcommunity.io

import java.util.*
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import me.antonio.noack.elementalcommunity.AllManager
import me.antonio.noack.elementalcommunity.api.WebServices
import java.net.URLEncoder
import android.os.Build
import android.os.Environment
import me.antonio.noack.elementalcommunity.cache.CombinationCache
import me.antonio.noack.webdroid.files.FileChooser
import me.antonio.noack.webdroid.files.FileSaver


object SaveLoadLogic {

    private val ignore = { key: String ->
        key.endsWith(".group") || key.endsWith(".name") || key.endsWith(".crafted")
    }

    fun init(all: AllManager){

        val clearRecipeCacheView = all.findViewById<View>(R.id.clearRecipeCache)
        clearRecipeCacheView?.setOnLongClickListener {
            AllManager.toast("The cache is updated automatically after one hour.", false)
            true
        }

        clearRecipeCacheView?.setOnClickListener {
            val edit = all.pref.edit()
            CombinationCache.invalidate(edit)
            edit.apply()
            AllManager.toast("Cleared Recipe Cache!", false)
        }

        all.findViewById<View>(R.id.saveProgress)?.setOnClickListener {

            val dialog = AlertDialog.Builder(all)
                .setView(R.layout.progress_save)
                .show()

            dialog.findViewById<View>(R.id.save)?.setOnClickListener {
                save(all)
                dialog.dismiss()
            }

            dialog.findViewById<View>(R.id.upload)?.setOnClickListener {
                upload(all)
                dialog.dismiss()
            }

            dialog.findViewById<View>(R.id.back)?.setOnClickListener {
                dialog.dismiss()
            }

        }

        all.findViewById<View>(R.id.loadProgress)?.setOnClickListener {

            val dialog = AlertDialog.Builder(all)
                .setView(R.layout.progress_load)
                .show()

            dialog.findViewById<View>(R.id.load)?.setOnClickListener {
                load(all)
                dialog.dismiss()
            }

            dialog.findViewById<View>(R.id.download)?.setOnClickListener {
                download(all)
                dialog.dismiss()
            }

            dialog.findViewById<View>(R.id.back)?.setOnClickListener {
                dialog.dismiss()
            }

        }


    }

    const val IMAGE_SELECTED = 17

    fun load(all: AllManager){
        FileChooser.requestFile(all, "text/plain"){
            applyDownload(all, it)
        }
    }

    fun save(all: AllManager){
        thread {
            save(all, "text/plain", "elementalBackup.txt")
        }
    }

    val WRITE_EXT_STORAGE_CODE = 1561
    var onWriteAllowed: (() -> Unit)? = null

    fun save(all: AllManager, mimeType: String, displayName: String) {
        FileSaver.save(all, displayName, mimeType, Saver.save(all.pref, ignore))
    }

    fun download(all: AllManager){

        // download from server -> ask password :D
        val dialog = AlertDialog.Builder(all)
            .setView(R.layout.ask_password)
            .show()

        dialog.findViewById<View>(R.id.ok)?.setOnClickListener {
            download(all, dialog.findViewById<TextView>(R.id.password)!!.text.toString())
            dialog.dismiss()
        }

        dialog.findViewById<View>(R.id.back)?.setOnClickListener {
            dialog.dismiss()
        }

    }

    fun download(all: AllManager, password: String){

        AllManager.toast("loading...", false)

        // download from server
        thread {
            WebServices.tryCaptcha(all, "load=$password", { data ->
                if(data.isNotBlank() && data != "#404"){

                    if(data.startsWith("#ip")){

                        AllManager.toast("You need to use the same network (ip)!", true)

                    } else {

                        applyDownload(all, data)

                    }

                } else AllManager.toast("Save not found", true)
            })
        }

    }

    fun applyDownload(all: AllManager, data: String){

        all.runOnUiThread {

            val dialog = AlertDialog.Builder(all)
                .setView(R.layout.ask_override)
                .show()

            dialog.findViewById<View>(R.id.copy)?.setOnClickListener {
                Loader.load(data, all.pref, true)
                AllManager.toast(R.string.success, true)
                all.loadEverythingFromPreferences()
                dialog.dismiss()
            }

            dialog.findViewById<View>(R.id.merge)?.setOnClickListener {
                Loader.load(data, all.pref, false)
                AllManager.toast(R.string.success, true)
                all.loadEverythingFromPreferences()
                dialog.dismiss()
            }

            dialog.findViewById<View>(R.id.cancel)?.setOnClickListener {
                dialog.dismiss()
            }

        }

    }

    fun upload(all: AllManager){

        thread {

            val data = Saver.save(all.pref, ignore)

            AllManager.toast("loading...", false)

            WebServices.tryCaptchaLarge(all, "save=${URLEncoder.encode(data, "UTF-8")}","?u=${AllManager.customUUID}", { password ->
                all.runOnUiThread {

                    val dialog = AlertDialog.Builder(all)
                        .setView(R.layout.show_password)
                        .show()

                    dialog.findViewById<TextView>(R.id.password)!!.text = password
                    dialog.findViewById<View>(R.id.ok)?.setOnClickListener {
                        dialog.dismiss()
                    }

                }
            })

        }

    }

}