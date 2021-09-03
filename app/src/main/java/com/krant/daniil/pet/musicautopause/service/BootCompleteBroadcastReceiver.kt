package com.krant.daniil.pet.musicautopause.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootCompleteBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(this::class.simpleName, "Received BOOT_COMPLETE")
        context?.startForegroundService(Intent(context, VolumeListenService::class.java))
    }
}