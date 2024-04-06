package com.example.video_app

import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.util.Rational
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import androidx.activity.ComponentActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

class PIPActivity : ComponentActivity() {
    companion object {

        /** Intent action for media controls from Picture-in-Picture mode.  */
        private const val ACTION_MEDIA_CONTROL = "media_control"

        /** Intent extra for media controls from Picture-in-Picture mode.  */
        private const val EXTRA_CONTROL_TYPE = "control_type"

        /** The request code for play action PendingIntent.  */
        private const val REQUEST_PLAY = 1

        /** The request code for pause action PendingIntent.  */
        private const val REQUEST_PAUSE = 2

        /** The intent extra value for play action.  */
        private const val CONTROL_TYPE_PLAY = 1

        /** The intent extra value for pause action.  */
        private const val CONTROL_TYPE_PAUSE = 2

    }

    /** This shows the video.  */
    private lateinit var videoView: VideoView

    /** The bottom half of the screen; hidden on landscape  */
    private lateinit var scrollView: View

    /** A [BroadcastReceiver] to receive action item events from Picture-in-Picture mode.  */
    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action != ACTION_MEDIA_CONTROL) {
                return
            }

            // This is where we are called back from Picture-in-Picture action items.
            when (intent.getIntExtra(EXTRA_CONTROL_TYPE, 0)) {
                CONTROL_TYPE_PLAY -> videoView.play()
                CONTROL_TYPE_PAUSE -> videoView.pause()
            }
        }
    }

    private val videoListener = object : VideoView.VideoListener {
        override fun onVideoStarted() {
            // We are playing the video now. In PiP mode, we want to show an action item to pause
            // the video.
            updatePictureInPictureActions(
                R.drawable.ic_pause, labelPause,
                CONTROL_TYPE_PAUSE, REQUEST_PAUSE
            )
        }

        override fun onVideoStopped() {
            // The video stopped or reached its end. In PiP mode, we want to show an action item
            // to play the video.
            updatePictureInPictureActions(
                R.drawable.ic_play, labelPlay,
                CONTROL_TYPE_PLAY, REQUEST_PLAY
            )
        }

        override fun onVideoMinimized() {
            // The MovieView wants us to minimize it. We enter Picture-in-Picture mode now.
            minimize()
        }
    }

    private val labelPlay: String by lazy { getString(R.string.play) }
    private val labelPause: String by lazy { getString(R.string.pause) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.BLACK
        setContentView(R.layout.activity_pip)

        // View references
        videoView = findViewById(R.id.movie)
        scrollView = findViewById(R.id.scroll)

        // Set up the video; it automatically starts.
        videoView.mVideoListener = videoListener
        videoView.videoResourceId = R.raw.video2
        videoView.adjustViewBounds = true
        findViewById<Button>(R.id.pip).setOnClickListener {
            minimize()
        }
    }

    override fun onStop() {
        // On entering Picture-in-Picture mode, onPause is called, but not onStop.
        // For this reason, this is the place where we should pause the video playback.
        videoView.pause()
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        // Show the video controls so the video can be easily resumed.
        if (!isInPictureInPictureMode) {
            videoView.showControls()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        adjustFullScreen(newConfig)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            adjustFullScreen(resources.configuration)
        }
    }

    private fun updatePictureInPictureActions(
        iconId: Int, title: String,
        controlType: Int, requestCode: Int
    ) {
        // This is the PendingIntent that is invoked when a user clicks on the action item.
        // You need to use distinct request codes for play and pause, or the PendingIntent won't
        // be properly updated.
        val i = Intent(ACTION_MEDIA_CONTROL)
            .putExtra(EXTRA_CONTROL_TYPE, controlType)
        val pi = PendingIntent.getBroadcast(this, requestCode, i, PendingIntent.FLAG_IMMUTABLE)
        val icon = Icon.createWithResource(this, iconId)

//        val ic2 = Icon.createWithResource(this, R.drawable.ic_exit)
//        val pi2 = PendingIntent.getActivity(
//            this,
//            9,
//            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")),
//            PendingIntent.FLAG_IMMUTABLE
//        )
        val actions = listOf(
            RemoteAction(icon, title, title, pi),
//            RemoteAction(ic2, title, title, pi2),
        )

        val params = PictureInPictureParams.Builder()
            .setActions(actions)
            .build()

        // This is how you can update action items (or aspect ratio) for Picture-in-Picture mode.
        // Note this call can happen even when the app is not in PiP mode. In that case, the
        // arguments will be used for at the next call of #enterPictureInPictureMode.
        setPictureInPictureParams(params)
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        if (isInPictureInPictureMode) {
            // Starts receiving events from action items in PiP mode.
            registerReceiver(mReceiver, IntentFilter(ACTION_MEDIA_CONTROL), RECEIVER_NOT_EXPORTED)
        } else {
            // We are out of PiP mode. We can stop receiving events from it.
            unregisterReceiver(mReceiver)
            // Show the video controls if the video is not playing
            if (!videoView.isPlaying) {
                videoView.showControls()
            }
        }
    }

    /**
     * Enters Picture-in-Picture mode.
     */
    private fun minimize() {
        // Hide the controls in picture-in-picture mode.
        videoView.hideControls()
        // Calculate the aspect ratio of the PiP screen.
        val params = PictureInPictureParams.Builder()
            .setAspectRatio(Rational(videoView.width, videoView.height))
            .build()
        enterPictureInPictureMode(params)
    }

    /**
     * Adjusts immersive full-screen flags depending on the screen orientation.
     * @param config The current [Configuration].
     */
    private fun adjustFullScreen(config: Configuration) {
        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            windowInsetsController.systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            scrollView.visibility = View.GONE
            videoView.adjustViewBounds = false
        } else {
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
            windowInsetsController.systemBarsBehavior = BEHAVIOR_DEFAULT
            scrollView.visibility = View.VISIBLE
            videoView.adjustViewBounds = true
        }
    }
}