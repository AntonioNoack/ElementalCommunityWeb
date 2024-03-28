package java.text

import java.util.*

object DateFormat {
    fun getDateInstance() = DateFormat
    fun format(date: Date): String {
        return date.toLocaleDateString()
    }
}