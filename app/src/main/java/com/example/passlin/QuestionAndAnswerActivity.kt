package com.example.passlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class QuestionAndAnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_and_answer)


        val questionTV = findViewById<TextView>(R.id.question)
        questionTV.text = UserLocalStore(this).returnQ()

        val buttonGo = findViewById<Button>(R.id.goButton)
        val buttonAccept = findViewById<Button>(R.id.acceptButton)

        val answerET = findViewById<EditText>(R.id.answer)
        val password = findViewById<TextView>(R.id.password)
        val actualAnswer = UserLocalStore(this).returnA()


        buttonAccept.setOnClickListener {
            password.text = UserLocalStore(this).returnUser().password
        }
        buttonGo.setOnClickListener {
            if (actualAnswer == answerET.text.toString()) {
                startActivity(Intent(this,LoginActivity::class.java))
            }
            else {
                Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
