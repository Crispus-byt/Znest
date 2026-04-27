package com.crispus.znest

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

import android.widget.Button
import androidx.core.view.WindowInsetsCompat

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

        val post_problem=findViewById<Button>(R.id.post_problem)

            post_problem.setOnClickListener {

            val postaproblemIntent= Intent(applicationContext, postaproblem::class.java)

            startActivity(postaproblemIntent)
        }

        val solveaproblem=findViewById<Button>(R.id.solve_problem)

        solveaproblem.setOnClickListener {

            val solveaproblemIntent= Intent(applicationContext, solveaproblem::class.java)

            startActivity(solveaproblemIntent)
        }



    }




}