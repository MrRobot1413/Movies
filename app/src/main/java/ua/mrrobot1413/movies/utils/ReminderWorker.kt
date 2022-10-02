package ua.mrrobot1413.movies.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class ReminderWorker(val context: Context, val params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
//        if (inputData.getBoolean("isToRemind", false)) {
            NotificationHelper(context).createNotification(
                inputData.getString("title").toString(),
                inputData.getString("message").toString(),
                inputData.getInt("movieId", 0)
            )
//        }

        return Result.success()
    }
}