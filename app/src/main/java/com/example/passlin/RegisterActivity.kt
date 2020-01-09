package com.example.passlin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val nameET = findViewById<EditText>(R.id.name)
        val passwordET = findViewById<EditText>(R.id.password)
        val button = findViewById<Button>(R.id.regButton)

        button.setOnClickListener {
            val name = nameET.text.toString()
            val password = passwordET.text.toString()

            if (name != "" || password != "")  {
                val user = User(name, password)
                UserLocalStore(this).storeUserData(user)
                startActivity(Intent(this, SecretQuestion::class.java))
            }
        }
    }
}
