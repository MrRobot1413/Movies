package ua.mrrobot1413.movies.utils

import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar

object UIUtils {

    fun showSnackbar(view: View, text: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view, text, duration).show()
    }

    fun View.hide() {
        this.isVisible = false
    }

    fun View.show() {
        this.isVisible = true
    }
}