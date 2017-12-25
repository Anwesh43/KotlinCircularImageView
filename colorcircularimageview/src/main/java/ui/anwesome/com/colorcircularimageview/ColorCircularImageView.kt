package ui.anwesome.com.colorcircularimageview

import android.app.Activity
import android.content.Context
import android.view.View
import android.graphics.*
import android.view.MotionEvent
import android.view.ViewGroup

/**
 * Created by anweshmishra on 25/12/17.
 */
class ColorCircularImageView(ctx:Context,var bitmap: Bitmap,var color:Int):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = ColorCircularImageRenderer(this)
    override fun onDraw(canvas:Canvas) {
        renderer.draw(canvas,paint)
        renderer.update()
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap(event.x,event.y)
            }
        }
        return true
    }
    data class ColorCircularImage(var bitmap:Bitmap,var color:Int) {
        fun draw(canvas:Canvas,paint:Paint,scale:Float) {
            val w = bitmap.width.toFloat()
            val h = bitmap.height.toFloat()
            canvas.save()
            val path = Path()
            path.addCircle(w/2,h/2,Math.min(w,h)/2,Path.Direction.CCW)
            canvas.clipPath(path)
            canvas.save()
            canvas.translate(w/2,h/2)
            paint.color = Color.argb(100,Color.red(color),Color.green(color),Color.blue(color))
            canvas.drawBitmap(bitmap,-w/2,-h/2,paint)
            canvas.drawArc(RectF(-w/2,-h/2,w/2,h/2),0f,360*scale,true,paint)
            canvas.restore()
            canvas.restore()
        }
        fun handleTap(x:Float,y:Float):Boolean = x>=0 && x<=bitmap.width && y>=0 && y<=bitmap.height
    }
    data class ColorCircularImageContainer(var bitmap: Bitmap,var color:Int) {
        var colorCircularImage = ColorCircularImage(bitmap,color)
        val state = ColorCircularImageState()
        fun draw(canvas:Canvas,paint:Paint) {
            colorCircularImage.draw(canvas,paint,state.scale)
        }
        fun update(stopcb:(Float)->Unit) {
            state.update(stopcb)
        }
        fun startUpdating(x:Float,y:Float,startcb:()->Unit) {
            if(colorCircularImage.handleTap(x,y)) {
                state.startUpdating(startcb)
            }
        }
    }
    data class ColorCircularImageState(var scale:Float = 0f,var dir:Float = 0f,var prevScale:Float = 0f) {
        fun update(stopcb:(Float)->Unit) {
            scale += dir*0.1f
            if(Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(scale)
            }
        }
        fun startUpdating(startcb: () -> Unit) {
            dir = 1-2*scale
            startcb()
        }
    }
    data class ColorCircularImageRenderer(var view:ColorCircularImageView) {
        var colorCircularImageContainer = ColorCircularImageContainer(view.bitmap,view.color)
        var animator:SimpleAnimator = SimpleAnimator(view)
        fun update() {
            animator.update{
                colorCircularImageContainer.update{
                    animator.stop()
                }
            }
        }
        fun draw(canvas:Canvas,paint:Paint) {
            canvas.drawColor(Color.parseColor("#00212121"))
            colorCircularImageContainer.draw(canvas,paint)
        }
        fun handleTap(x:Float,y:Float) {
            colorCircularImageContainer.startUpdating(x,y,{
                animator.startAnimation()
            })
        }
    }
    data class SimpleAnimator(var view:View,var time:Long = 50) {
        var animated:Boolean = false
        fun update(updatecb:()->Unit) {
            if(animated) {
                updatecb()
                try {
                    view.invalidate()
                    Thread.sleep(time)
                }
                catch(ex:Exception) {

                }
            }
        }
        fun startAnimation() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
               animated = false
            }
        }
    }
    companion object {
        fun create(activity:Activity,bitmap:Bitmap,color:Int):ColorCircularImageView {
            val view = ColorCircularImageView(activity,bitmap,color)
            activity.addContentView(view,ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT))
            return view
        }
    }
}