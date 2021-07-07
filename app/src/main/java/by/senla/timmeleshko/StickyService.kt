package by.senla.timmeleshko

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
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

    override fun onCreate() {
        Log.i(STICKY_SERVICE, "Service created!")
        addNotification()
        super.onCreate()
    }

    override fun onDestroy() {
        Log.i(STICKY_SERVICE, "Service removed!")
        stopForeground(true)
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.i(STICKY_SERVICE, "Client bounded!")
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(STICKY_SERVICE, "All clients unbounded!")
        return super.onUnbind(intent)
    }

    /**
     * Вызывается системой каждый раз, когда клиент явно запускает службу, вызывая Context.startService.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY /* Если процесс этой службы завершается после OnStart, оставляет его в
        исходном состоянии, но не сохраняет его доставленный Intent. Позже система попытается воссоздать службу.
        Поскольку она находится в запущенном состоянии, она гарантирует вызов onStartCommand после создания
        нового экземпляра. Этот режим имеет смысл для вещей, которые будут явно запускаться и останавливаться
        для выполнения в течение произвольных периодов времени, таких как служба, выполняющая воспроизведение фоновой музыки. */
    }

    private fun addNotification() {
        val channel = NotificationChannel(STICKY_SERVICE_CHANNEL, STICKY_SERVICE, NotificationManager.IMPORTANCE_LOW)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(this, STICKY_SERVICE_CHANNEL)
            .setContentTitle(STICKY_SERVICE)
            .setContentText(RUNNING)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
        val intent = Intent(this, StickyService::class.java)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        builder.setContentIntent(pendingIntent)
        startForeground(1, builder.build())
    }
}