package com.lysenko.myapplication.ui.castomViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.lysenko.myapplication.R

class StatisticCustomView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val arcPaint2 = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectF = RectF()

    private var cx = 0F
    private var cy = 0F
    private var radius = 0F
    private var radius2circle = 0F

    init {
        arcPaint.color = ContextCompat.getColor(context, R.color.green)
        arcPaint2.color = ContextCompat.getColor(context, R.color.yellow)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val modeWidth = MeasureSpec.getMode(widthMeasureSpec)

        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)

        var customSizeHeight = 0
        var customSizeWidth = 0

        when (modeWidth) {
            MeasureSpec.EXACTLY -> {
                customSizeWidth = sizeWidth
                customSizeHeight = sizeHeight
            }
            MeasureSpec.AT_MOST -> {
                customSizeWidth = sizeWidth * 8 / 10
                customSizeHeight = sizeHeight
            }
            MeasureSpec.UNSPECIFIED -> {
                Log.e("AAA", "UNSPECIFIED")
            }
        }
        setMeasuredDimension(customSizeWidth, customSizeHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cx = width / 2F
        cy = height / 2F
        radius = Math.min(width, height) / 4F

        rectF.left = 0F
        rectF.top = radius
        rectF.right = radius * 2
        rectF.bottom = radius * 3
    }

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
        canvas ?: return

        canvas.drawArc(rectF, -90F, 360F, true, arcPaint)
        invalidate()
//
//        val width = view.width
//        val height = view.height
//
//        cx = width / 2F
//        cy = height / 2F
//        radius = Math.min(width, height) / 4F
//        rectF.left = 0F
//        rectF.top = radius
//        rectF.right = radius * 2
//        rectF.bottom = radius * 3
//
//        arcPaint.isAntiAlias = true
//        arcPaint.color = resources.getColor(R.color.red)
//        canvas.drawArc(rectF, -90F, 360F, true, arcPaint)



//        canvas.drawCircle(cx, cy, radius2circle, circle2Paint)

//        canvas.save()
//
//        for (i in 1..12) {
//
//            canvas.rotate(30F, cx, cy)
//        }
//
//        canvas.restore()
//
//        canvas.save()
//
////        canvas.rotate(
////
////            (dateInHoursFormatted.toFloat() + dateInMinutesFormatted.toFloat() / 60) * 30,
////            cx, cy
////
////        )
//
//
//        canvas.restore()
//
//        canvas.save()
//
////        canvas.rotate(dateInMinutesFormatted.toFloat() * 6, cx, cy)
//

//
//        canvas.restore()
//
//        canvas.drawCircle(cx, cy, 20F, circlePaint)
//
//        canvas.save()
//
//        canvas.scale(4F, 4F)
//
////        canvas.drawText(currientDate, cx / 4 - cx / 8, cy / 4 + cy / 7, rectPaint)
//
//        canvas.restore()
    }
}