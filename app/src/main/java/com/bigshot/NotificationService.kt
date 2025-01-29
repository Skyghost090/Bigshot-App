package com.bigshot

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock.sleep
import androidx.core.app.NotificationCompat

class NotificationService : Service() {
    private fun run_(){
        val intentNotify = Intent(this, ManageActivity::class.java)
        val pendingIntentNotify = PendingIntent.getActivity(this, 3, intentNotify,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        val notification_ = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val ChannelID = "2"
        val builder = NotificationCompat.Builder(this, ChannelID)
        val notificationChannel = NotificationChannel(ChannelID,
            "Bigshot",
            NotificationManager.IMPORTANCE_DEFAULT);
        notification_.createNotificationChannel(notificationChannel)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Bigshot").setContentText("Touch to enable service")
            .setOngoing(true)
            .setAutoCancel(false)
            .addAction(R.drawable.ic_launcher_foreground, "Manage", pendingIntentNotify)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setSilent(true)

        notification_.notify(2, builder.build())
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { onTaskRemoved(it) }
        run_()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        Thread{
            sleep(5000)
            val restartServiceIntent = Intent(applicationContext, this.javaClass)
            restartServiceIntent.setPackage(packageName)
            startService(restartServiceIntent)
            super.onTaskRemoved(rootIntent)
        }.start()
    }
}