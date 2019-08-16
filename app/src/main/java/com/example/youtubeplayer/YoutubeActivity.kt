package com.example.youtubeplayer


import android.app.IntentService
import android.app.Service
import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import kotlin.concurrent.thread

class YoutubeActivity : YouTubeBaseActivity(),YouTubePlayer.OnInitializedListener {
    private lateinit var wakeLock:PowerManager.WakeLock
    companion object {
        lateinit var YOUTUBE_VIDEO_ID: String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"YoutubeActivity::Background_Audio").apply { acquire() }
        }
        val layout = layoutInflater.inflate(R.layout.activity_youtube,null) as ConstraintLayout
        setContentView(layout)

        val playerView = YouTubePlayerView(this)
        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.addView(playerView)

        playerView.initialize(getString(R.string.GOOGLE_API_KEY),this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youtubePlayer: YouTubePlayer?, wasRestored: Boolean) {

        if(!wasRestored){
            youtubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
        }else{
            youtubePlayer?.play()
        }
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
        val REQUEST_CODE = 0

        if(p1?.isUserRecoverableError==true){
            p1.getErrorDialog(this,REQUEST_CODE).show()
        }else{
            val errorMessage="failed to initialize player {$p1}"
            Toast.makeText(this,errorMessage,Toast.LENGTH_LONG).show()
        }

        thread { Thread.sleep(2000)
            setContentView(R.layout.activity_main)
        }.start()

    }






}