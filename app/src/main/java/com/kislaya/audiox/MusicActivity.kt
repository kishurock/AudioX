package com.kislaya.audiox

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import java.util.concurrent.TimeUnit
import kotlin.math.max

class MusicActivity : AppCompatActivity(), Runnable {
    lateinit var txtSongName: TextView
    lateinit var imgSongImage: ImageView
    lateinit var imgFastRewind: ImageView
    lateinit var imgPlay: ImageView
    lateinit var imgStop: ImageView
    lateinit var imgFastForward: ImageView
    lateinit var seekBar: SeekBar
    lateinit var relativeLayout: RelativeLayout
    lateinit var txtTimeStop: TextView
    lateinit var txtTimeStart: TextView
    lateinit var mediaPlayer: MediaPlayer
    var forwardTime: Int = 5000
    var backwardTime: Int = 5000
    var songImage: Int = 100
    var songTitle: String? = "hello"
    var song: Int = 200
    var startTime: Int = 0
    var finalTime: Int = 0
    var handler = Handler()
    var oneTimeOnly: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        txtSongName = findViewById(R.id.txtSongName)
        imgSongImage = findViewById(R.id.imgSongImage)
        imgFastForward = findViewById(R.id.imgFastForward)
        imgFastRewind = findViewById(R.id.imgFastRewind)
        imgPlay = findViewById(R.id.imgPlay)
        imgStop = findViewById(R.id.imgStop)
        seekBar = findViewById(R.id.seekBar)
        relativeLayout = findViewById(R.id.relativeLayout)
        txtTimeStart = findViewById(R.id.txtTimeStart)
        txtTimeStop = findViewById(R.id.txtTimeStop)

        if (intent != null) {
            songImage = intent.getIntExtra("songImage", 0)
            songTitle = intent.getStringExtra("songTitle")
            song = intent.getIntExtra("song", 0)

        } else {
            finish()
            Toast.makeText(
                this@MusicActivity,
                "Some unexpected error occurred",
                Toast.LENGTH_LONG
            ).show()
        }
        if (song == 200 || songImage == 100 || songTitle == "hello") {
            finish()
            Toast.makeText(
                this@MusicActivity,
                "Some unexpected error occurred",
                Toast.LENGTH_LONG
            ).show()

        }
        txtSongName.text = songTitle
        imgSongImage.setImageResource(songImage)

        mediaPlayer = MediaPlayer.create(this, song)
        seekBar.isClickable = false
        mediaPlayer.start()
        finalTime = mediaPlayer.duration
        startTime = mediaPlayer.currentPosition
        if (oneTimeOnly == 0) {
            seekBar.max = finalTime
            oneTimeOnly = 1
        }
        txtTimeStop.text =
            "${TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong())}:${TimeUnit.MILLISECONDS.toSeconds(
                finalTime.toLong()
            ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()))}"

        txtTimeStart.text =
            "${TimeUnit.MILLISECONDS.toMinutes(startTime.toLong())}:${TimeUnit.MILLISECONDS.toSeconds(
                startTime.toLong()
            ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()))}"

        seekBar.progress = startTime
        handler.postDelayed(updateSongTime, 100)

        imgPlay.setOnClickListener {
            mediaPlayer.start()
            finalTime = mediaPlayer.duration
            startTime = mediaPlayer.currentPosition
            if (oneTimeOnly == 0) {
                seekBar.max = finalTime
                oneTimeOnly = 1
            }
            txtTimeStop.text =
                "${TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong())}:${TimeUnit.MILLISECONDS.toSeconds(
                    finalTime.toLong()
                ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(finalTime.toLong()))}"

            txtTimeStart.text =
                "${TimeUnit.MILLISECONDS.toMinutes(startTime.toLong())}:${TimeUnit.MILLISECONDS.toSeconds(
                    startTime.toLong()
                ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()))}"

            seekBar.progress = startTime
            handler.postDelayed(updateSongTime, 100)
            imgPlay.visibility = View.GONE
            imgStop.visibility = View.VISIBLE
        }
        imgStop.setOnClickListener {
            mediaPlayer.pause()
            imgStop.visibility = View.GONE
            imgPlay.visibility = View.VISIBLE
        }
        imgFastForward.setOnClickListener {
            val temp = startTime
            if ((temp + forwardTime) <= finalTime) {
                startTime += forwardTime
                mediaPlayer.seekTo(startTime)

            }

        }
        imgFastRewind.setOnClickListener {
            val temp = startTime
            if ((temp - backwardTime) > 0) {
                startTime -= backwardTime
                mediaPlayer.seekTo(startTime)

            }
        }
        relativeLayout.setOnTouchListener(object : OnSwipeTouchListener(this@MusicActivity) {
            override fun onSwipeRight() {
                super.onSwipeRight()
                onBackPressed()
            }
        })
    }

    val updateSongTime = Runnable {
        run()
    }

    override fun run() {
        startTime = mediaPlayer.currentPosition
        txtTimeStart.text =
            "${TimeUnit.MILLISECONDS.toMinutes(startTime.toLong())}:${TimeUnit.MILLISECONDS.toSeconds(
                startTime.toLong()
            ) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime.toLong()))}"
        seekBar.progress = startTime
        handler.postDelayed(this@MusicActivity, 100)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer.stop()
        finish()
    }
}
