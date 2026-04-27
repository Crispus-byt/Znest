package com.crispus.znest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mainmenu=findViewById<Button>(R.id.main_menu)

        mainmenu.setOnClickListener {

            val mainmenuintent= Intent(applicationContext, MainActivity::class.java)

            startActivity(mainmenuintent)
        }

        val changephoto=findViewById<Button>(R.id.change_photo)

        changephoto.setOnClickListener {

            val api="httttt"


        }
    }
}