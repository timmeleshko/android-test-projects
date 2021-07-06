package by.senla.timmeleshko

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import by.senla.timmeleshko.StickyService.StickyServiceConstants.RUNNING
import by.senla.timmeleshko.StickyService.StickyServiceConstants.STICKY_SERVICE
import by.senla.timmeleshko.StickyService.StickyServiceConstants.STICKY_SERVICE_CHANNEL

class StickyService : Service() {

    object StickyServiceConstants {
        const val STICKY_SERVICE = "StickyService"
        const val STICKY_SERVICE_CHANNEL = "StickyServiceChannel"
        const val RUNNING = "StickyService is running..."
    }

    override fun onBind(intent: Intent): IBinder? {
        addNotification()
        Log.i("E", "Bounded!")
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        stopForeground(true)
        Log.i("E", "Unbounded!")
        return super.onUnbind(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun addNotification() {
        val mNotificationBuilder = NotificationCompat.Builder(this, STICKY_SERVICE_CHANNEL)
            .setContentTitle(STICKY_SERVICE)
            .setContentText(RUNNING)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
        val intent = Intent(this, StickyService::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        mNotificationBuilder.setContentIntent(pendingIntent)
        startForeground(1, mNotificationBuilder.build())
    }
}