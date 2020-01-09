package com.example.passlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SecretQuestion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret_question)

        val button = findViewById<Button>(R.id.sbmButton)
        val questionET = findViewById<EditText>(R.id.question)
        val answerET = findViewById<EditText>(R.id.answer)

        button.setOnClickListener {
            val question = questionET.text.toString()
            val answer = answerET.text.toString()

            if (question != "" || answer != "")  {

                UserLocalStore(this).storeAnswer(answer)
                UserLocalStore(this).storeQuestion(question)
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}
