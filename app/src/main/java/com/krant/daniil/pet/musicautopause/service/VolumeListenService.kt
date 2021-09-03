package com.krant.daniil.pet.musicautopause.service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import android.app.NotificationManager

import android.app.NotificationChannel
import android.content.Context
import android.graphics.Color
import com.krant.daniil.pet.musicautopause.R

class VolumeListenService : Service() {
    private lateinit var volumeChangeObserver: VolumeChangeObserver

    override fun onBind(intent: Intent): IBinder? {
        return null;
    }

    override fun onCreate() {
        super.onCreate()
        runInForeground()
        registerVolumeListener()
    }

    private fun runInForeground() {
        val channelId = "com.krant.daniil.pet.musicautopause"
        val notificationId = 42
        val channelName = "musicautopause"
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_NONE
        )
        channel.lightColor = Color.WHITE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(channel)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification: Notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.drawable.outline_pause_circle_outline_24)
            .setContentTitle(applicationContext.getString(R.string.notification_text))
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(notificationId, notification)
    }

    private fun registerVolumeListener() {
        volumeChangeObserver = VolumeChangeObserver(Handler(), this)
        applicationContext.contentResolver.registerContentObserver(
            android.provider.Settings.System.CONTENT_URI,
            true, volumeChangeObserver
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(this::class.simpleName, "Service Destroyed")
        applicationContext.contentResolver.unregisterContentObserver(volumeChangeObserver)
    }
}
