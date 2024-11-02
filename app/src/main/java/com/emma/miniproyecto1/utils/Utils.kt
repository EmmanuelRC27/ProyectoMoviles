package com.emma.miniproyecto1.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    // Método para validar una fecha en formato específico
    fun isValidDate(dateString: String, format: String = "yyyy-MM-dd"): Boolean {
        return try {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(dateString)
            true
        } catch (e: Exception) {
            false
        }
    }

    // Método para formatear una fecha
    fun formatDate(date: Date, format: String = "yyyy-MM-dd"): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(date)
    }
}
