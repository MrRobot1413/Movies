package ua.mrrobot1413.movies.utils

import kotlin.math.pow
import kotlin.math.roundToInt

object Utils {

    fun Float.roundTo(numFractionDigits: Int): Double {
        val factor = 10.0.pow(numFractionDigits.toDouble())
        return (this * factor).roundToInt() / factor
    }
}