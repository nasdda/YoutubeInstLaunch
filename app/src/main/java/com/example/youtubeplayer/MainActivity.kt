package com.example.youtubeplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException



class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnSingle.setOnClickListener(this)
        btnPlaylist.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val urlPassed = urlTextView.text.toString()
        if(view.id == R.id.btnSingle && (urlPassed.matches(Regex(pattern = "(https://youtu.be/).*|.*(youtu.be/).*")))){
            val parts = urlPassed.split("tu.be/")
            YoutubeActivity.YOUTUBE_VIDEO_ID = parts[1]
            startActivity(Intent(this,YoutubeActivity::class.java))
            return
        }
        else if(view.id == R.id.btnSingle &&(urlPassed.startsWith("https://www.youtube.com/watch?v=") || urlPassed.startsWith("www.youtube.com/watch?v="))){
            val parts = urlPassed.split(".com/watch?v=")
            YoutubeActivity.YOUTUBE_VIDEO_ID = parts[1]
            startActivity(Intent(this,YoutubeActivity::class.java))
            return
        }
        else if(view.id == R.id.btnPlaylist && (urlPassed.startsWith("https://www.youtube.com/playlist?list=") || urlPassed.startsWith("www.youtube.com/playlist?list="))){
            val parts = urlPassed.split(".com/playlist?list=")
            YoutubePlaylistActivity.YOUTUBE_PLAYLIST = parts[1]
            startActivity(Intent(this,YoutubePlaylistActivity::class.java))
            return
        }else{
            val btn =  if (view.id == R.id.btnSingle) "video"  else "playlist"
            Toast.makeText(this,"Invalid $btn URL",Toast.LENGTH_LONG).show()
            return;
        }
    }


    override fun onStop() {
        super.onStop()
        if (urlTextView.text.isNotEmpty()){
            val myPref = MyPref(this)
            myPref.savePref(urlTextView.text.toString())
        }

    }

    override fun onStart() {
        super.onStart()
        val myPref = MyPref(this)
        urlTextView.setText(myPref.getPref())
    }
}
