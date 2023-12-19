package com.example.seven.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.seven.R
import java.util.concurrent.TimeUnit

class Notification(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = try {
        show()
        Result.success()
    } catch (e: Exception) {
        Result.failure()
    }


    @SuppressLint("MissingPermission")
    private fun show() {
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(NOTIFICATION_ID, NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Пора загрузить новое фото!")
                .setContentText("Вы давно не загружали фото. Добавьте что-то новенькое!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build())
        }
    }

    companion object {
        const val WORK_NAME = "Notification"
        const val CHANNEL_ID = "YourAppChannelId"
        const val NOTIFICATION_ID = 1

        fun schedule(context: Context) {
            WorkManager.getInstance(context)
                .enqueue(PeriodicWorkRequestBuilder<Notification>(
                    1, TimeUnit.DAYS
                ).build())
        }
    }
}
