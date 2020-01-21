package com.shanya.neondemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference
import java.util.*
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {

    val names: Array<Int> = arrayOf(R.id.view01,R.id.view02,R.id.view03,R.id.view04,R.id.view05,R.id.view06)
    var views = arrayOfNulls<TextView>(names.size)
//    var views : Array<TextView> = arrayOf(view01,view03,view03,view04,view05,view06)

    inner class MyHandler(private var activity: WeakReference<MainActivity>) : Handler() {
        private var currentColor:Int = 0

        val colors: Array<Int> = arrayOf(R.color.color1,R.color.color2,R.color.color3,R.color.color4,R.color.color5,R.color.color6)
        override fun handleMessage(msg: Message) {

            if (msg.what == 0x123) {
                for (i in activity.get()!!.views.indices){
                    activity.get()!!.views[i]?.setBackgroundResource(colors[(i + currentColor) % colors.size])
                }
                currentColor++;
            }
            super.handleMessage(msg)
        }
    }

    private val handler = MyHandler(WeakReference(this))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in names.indices){
            views[i] = findViewById(names[i])
        }

        Timer().schedule(timerTask {
            handler.sendEmptyMessage(0x123)
        },0,200)
    }
}
