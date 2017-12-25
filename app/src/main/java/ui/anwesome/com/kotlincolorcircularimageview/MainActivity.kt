package ui.anwesome.com.kotlincolorcircularimageview

import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.colorcircularimageview.ColorCircularImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ColorCircularImageView.create(this,BitmapFactory.decodeResource(resources,R.drawable.trphy),Color.parseColor("#311B92"))
    }
}
