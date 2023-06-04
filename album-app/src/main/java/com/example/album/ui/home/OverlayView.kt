package com.example.album.ui.home

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.withStyledAttributes
import androidx.core.view.doOnLayout
import com.example.album.R

class OverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    private var cornerRadius = 0f
    private var targetViewId = -1
    private var framePath = Path()
    private var fillFrame = false
    private val fillPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val framePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.OverlayView) {
            framePaint.strokeWidth = getDimension(R.styleable.OverlayView_frameWidth, 8f)
            fillPaint.color = getColor(R.styleable.OverlayView_backgroundColor, Color.BLACK)
            framePaint.color = getColor(R.styleable.OverlayView_frameColor, Color.WHITE)
            targetViewId = getResourceId(R.styleable.OverlayView_targetView, targetViewId)
            cornerRadius = getDimension(R.styleable.OverlayView_cornerRadius, 16f)
            fillFrame = getBoolean(R.styleable.OverlayView_fillFrame, false)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        log("root onLayout ${this.bounds()}")
        (parent as View).findViewById<View>(targetViewId)?.doOnLayout {
            onFrameSizeChanged(it)
            log("frame onLayout ${it.bounds()}")
        }
    }

    private fun onFrameSizeChanged(v: View) = framePath.apply {
        reset()
        addRoundRect(
            v.left.toFloat(), v.top.toFloat(), v.right.toFloat(), v.bottom.toFloat(),
            cornerRadius, cornerRadius, Path.Direction.CW
        )
        close()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        log("onDraw ")

        canvas.save()
        canvas.clipOutPath(framePath)
        // Draws the dark background scrim and leaves the box area clear
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), fillPaint)

        canvas.restore()

        if (fillFrame) {
            canvas.drawPath(framePath, fillPaint)
        }
        canvas.drawPath(framePath, framePaint)
    }

    private fun View.bounds() = Rect(left, top, right, bottom)

    private fun log(a: Any?) {
        Log.d("OverlayView", a.toString())
    }
}
