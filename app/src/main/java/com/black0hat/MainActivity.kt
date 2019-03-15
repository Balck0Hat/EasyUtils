package com.black0hat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.black0hat.customutils.MyUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, "${MyUtils.Minus(1, 2)}", Toast.LENGTH_LONG).show()

    }
}