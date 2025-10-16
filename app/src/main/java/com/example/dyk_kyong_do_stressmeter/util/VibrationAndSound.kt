package com.example.dyk_kyong_do_stressmeter.util

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.VibrationEffect
import android.os.Vibrator
import com.example.dyk_kyong_do_stressmeter.R

object VibrationAndSound {
    
    fun onStart(context: Context) {
        try {
            val mediaPlayer = MediaPlayer.create(context, R.raw.nokia_startup_sound)
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            mediaPlayer.setVolume(1.0f, 1.0f)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                mediaPlayer.release()
            }
        } catch (e: Exception) {
            try {
                val toneGenerator = android.media.ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100)
                toneGenerator.startTone(android.media.ToneGenerator.TONE_PROP_BEEP, 200)
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    toneGenerator.release()
                }, 300)
            } catch (e2: Exception) {
                // Both sound methods failed
            }
        }

        try {
            val vib = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (android.os.Build.VERSION.SDK_INT >= 26) {
                vib.vibrate(VibrationEffect.createOneShot(120, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vib.vibrate(120)
            }
        } catch (e: Exception) {
            // Vibration failed
        }
    }
}