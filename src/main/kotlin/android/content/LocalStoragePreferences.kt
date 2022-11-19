package android.content

import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.browser.localStorage

class LocalStoragePreferences: SharedPreferences {

    override val all: Map<String, *>
        get() = getMap()

    fun getMap(): Map<String, *> {
        val map = HashMap<String, Any?>()
        for(i in 0 until localStorage.length){
            val key = localStorage.key(i) ?: ""
            map[key] = localStorage[key]
        }
        return map
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        val value = localStorage[key] ?: return defValue
        return value.toBoolean()
    }

    override fun getString(key: String, defValue: String): String {
        return localStorage[key] ?: defValue
    }

    override fun getString(key: String, defValue: Any?): String? {
        return localStorage[key] ?: defValue as? String
    }

    override fun contains(key: String): Boolean = localStorage[key] != null

    fun ix(text: String, char: Char, index0: Int): Int {
        val ix = text.indexOf(char, index0)
        return if(ix < 0) text.length else ix
    }

    override fun getFloat(key: String, defValue: Float): Float = localStorage[key]?.toFloatOrNull() ?: defValue
    override fun getInt(key: String, defValue: Int): Int = localStorage[key]?.toIntOrNull() ?: defValue
    override fun getLong(key: String, defValue: Long): Long = localStorage[key]?.toLongOrNull() ?: defValue
    override fun getStringSet(key: String, defValues: Set<String>): Set<String> {
        val text = localStorage[key] ?: return defValues
        val ret = HashSet<String>()
        var i=0
        while(true){
            val j = ix(text, ';', i)
            if(j >= text.length) break
            val count = text.substring(i, j).toIntOrNull() ?: break
            ret.add(text.substring(j+1, j+count+1))
            i = j+count+1
            if(text[i] != ';') break
            i++
        }
        return ret
    }

    override fun edit(): SharedPreferences.Editor {
        return object: SharedPreferences.Editor {
            override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
                localStorage[key] = value.toString()
                return this
            }
            override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
                localStorage[key] = value.toString()
                return this
            }
            override fun putInt(key: String, value: Int): SharedPreferences.Editor {
                localStorage[key] = value.toString()
                return this
            }
            override fun putLong(key: String, value: Long): SharedPreferences.Editor {
                localStorage[key] = value.toString()
                return this
            }
            override fun putString(key: String, value: String): SharedPreferences.Editor {
                localStorage[key] = value
                return this
            }
            override fun putStringSet(key: String, values: Set<String>): SharedPreferences.Editor {
                localStorage[key] = values.joinToString { "${it.length};$it;" }
                return this
            }
            override fun remove(key: String): SharedPreferences.Editor {
                localStorage.removeItem(key)
                return this
            }
            override fun clear(): SharedPreferences.Editor {
                localStorage.clear()
                return this
            }
            override fun commit(): Boolean {
                return true
            }
            override fun apply() {}
        }
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        throw RuntimeException("not implemented")
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        throw RuntimeException("not implemented")
    }



}