package com.example.video_app

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.lang.ref.WeakReference

class TimeoutHandler(view: VideoView) : Handler(Looper.getMainLooper()) {
    private val mMovieViewRef: WeakReference<VideoView> = WeakReference(view)

    override fun handleMessage(msg: Message) {
        when (msg.what) {
            MESSAGE_HIDE_CONTROLS -> {
                mMovieViewRef.get()?.hideControls()
            }

            else -> super.handleMessage(msg)
        }
    }

    companion object {
        internal val MESSAGE_HIDE_CONTROLS = 1
    }
}