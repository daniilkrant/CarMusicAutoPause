package com.krant.daniil.pet.musicautopause.service

import android.content.Context
import android.database.ContentObserver
import android.media.AudioManager
import android.os.Handler
import android.util.Log
import android.view.KeyEvent

class VolumeChangeObserver(handler: Handler?, c : Context) : ContentObserver(handler) {
    private val context = c
    private var isForcefullyPaused = false

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)

        val audioManager = context.getSystemService(Context.AUDIO_SERVICE)
        if (audioManager !is AudioManager) return
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        Log.w(this::class.simpleName, "Volume: $currentVolume")

        if (currentVolume == 0) {
            val event = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PAUSE)
            audioManager.dispatchMediaKeyEvent(event)
            isForcefullyPaused = true
        }

        if (currentVolume > 0 && isForcefullyPaused) {
            val event = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY)
            audioManager.dispatchMediaKeyEvent(event)
            isForcefullyPaused = false
        }
    }
}