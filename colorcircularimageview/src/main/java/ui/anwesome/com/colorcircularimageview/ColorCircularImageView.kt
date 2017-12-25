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
    class ColorCirularImage(var bitmap:Bitmap) {
        fun draw(canvas:Canvas,paint:Paint,scale:Float) {
            val w = bitmap.width.toFloat()
            val h = bitmap.height.toFloat()
            canvas.save()
            val path = Path()
            path.addCircle(w/2,h/2,Math.min(w,h)/2,Path.Direction.CCW)
            canvas.clipPath(path)
            canvas.save()
            canvas.translate(w/2,h/2)
            canvas.drawBitmap(bitmap,-w/2,-h/2,paint)
            canvas.drawArc(RectF(-w/2,-h/2,w/2,h/2),0f,360*scale,true,paint)
            canvas.restore()
            canvas.restore()
        }
        fun handleTap(x:Float,y:Float):Boolean = x>=0 && x<=bitmap.width && y>=0 && y<=bitmap.height
    }
}