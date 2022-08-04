package ua.mrrobot1413.movies.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

    fun showSoftKeyboard(activity: AppCompatActivity, view: View) {
        if (view.requestFocus()) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideKeyboard(activity: AppCompatActivity, view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun TextView.toggleReadMoreTextView(viewGroup: ViewGroup, linesWhenCollapsed: Int) {
        if (this.maxLines != Integer.MAX_VALUE) {
            // exapand
            this.maxLines = Integer.MAX_VALUE
        } else {
            // collapse
            this.maxLines = linesWhenCollapsed
        }
        // start animation
        TransitionManager.beginDelayedTransition(viewGroup)
    }
}