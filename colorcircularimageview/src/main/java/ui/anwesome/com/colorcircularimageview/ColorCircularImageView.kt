package ui.anwesome.com.colorcircularimageview

import android.content.Context
import android.view.View
import android.graphics.*
import android.view.MotionEvent

/**
 * Created by anweshmishra on 25/12/17.
 */
class ColorCircularImageView(ctx:Context,var bitmap: Bitmap):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}