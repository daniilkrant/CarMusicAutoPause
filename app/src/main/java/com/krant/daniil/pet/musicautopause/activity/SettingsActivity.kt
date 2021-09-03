package com.krant.daniil.pet.musicautopause.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.krant.daniil.pet.musicautopause.R
import com.krant.daniil.pet.musicautopause.service.VolumeListenService

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        findViewById<TextView>(R.id.author_ln).setOnClickListener { _ ->
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://linkedin.com/in/daniilkrant")
            )
            startActivity(browserIntent)
        }

        findViewById<TextView>(R.id.author_tg).setOnClickListener { _ ->
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://t.me/DaniilDaniilXYZ")
            )
            startActivity(browserIntent)
        }

        findViewById<TextView>(R.id.start_service_button).setOnClickListener { _ ->
            startForegroundService(Intent(this, VolumeListenService::class.java))
        }

        findViewById<TextView>(R.id.stop_service_button).setOnClickListener { _ ->
            applicationContext.stopService(Intent(this, VolumeListenService::class.java))
        }
    }
}