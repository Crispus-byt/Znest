package com.crispus.znest

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

import android.widget.Button
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val Signin=findViewById<Button>(R.id.signinbutton)

        Signin.setOnClickListener {

            val SigninIntent= Intent(applicationContext, signin::class.java)

            startActivity(SigninIntent)
        }

        val Signup=findViewById<Button>(R.id.signupbutton)

        Signup.setOnClickListener {

            val SignupIntent= Intent(applicationContext, signup::class.java)

            startActivity(SignupIntent)
        }

        val postproblem=findViewById<Button>(R.id.post_problem)

            postproblem.setOnClickListener {

            val postaproblemIntent= Intent(applicationContext, postaproblem::class.java)

            startActivity(postaproblemIntent)
        }

        val solveaproblem=findViewById<Button>(R.id.solve_problem)

        solveaproblem.setOnClickListener {

            val solveaproblemIntent= Intent(applicationContext, solveaproblem::class.java)

            startActivity(solveaproblemIntent)
        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_explore -> true
                R.id.nav_add -> true
                R.id.nav_notifications -> true
                R.id.nav_profile -> true
                else -> false
            }
        }

        bottomNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }

                R.id.nav_explore -> {
                    startActivity(Intent(this, solveaproblem::class.java))
                    finish()
                    true
                }

                R.id.nav_add -> {
                    startActivity(Intent(this, postaproblem::class.java))
                    finish()
                    true
                }

                R.id.nav_notifications -> {
                    startActivity(Intent(this, profile::class.java))
                    finish()
                    true
                }

                R.id.nav_profile -> {
                    startActivity(Intent(this, profilepage::class.java))
                    finish()
                    true
                }

                else -> false
            }
        }




    }




}