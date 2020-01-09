package com.example.passlin

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.passlin.CardsActivity.Companion.dataList
import com.example.passlin.CardsActivity.Companion.rvAdapter

import java.text.SimpleDateFormat
import java.util.*


class CardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        val actionBarTitle = findViewById<TextView>(R.id.action_bar_title)
        actionBarTitle.text = "Add"

        val icon = findViewById<ImageView>(R.id.icon)
        var generator = ColorGenerator.MATERIAL
        var color = generator.randomColor
        icon.setImageDrawable(
            TextDrawable.builder().buildRound("",
                color))

        icon.setOnClickListener {
            generator = ColorGenerator.MATERIAL
            color = generator.randomColor
            icon.setImageDrawable(
                TextDrawable.builder().buildRound("",
                    color))
        }


        val backButton = findViewById<ImageView>(R.id.action_bar_back)
        backButton.setOnClickListener {
            startActivity(Intent(this, CardsActivity::class.java))
        }

        val addButton = findViewById<ImageView>(R.id.action_bar_add)
        val titleET = findViewById<EditText>(R.id.added_title)
        val loginET: EditText? = findViewById(R.id.added_login)
        val passwordET: EditText? = findViewById(R.id.added_password)
        val noteET: EditText? = findViewById(R.id.added_note)

        addButton.setOnClickListener {
            val title = titleET!!.text.toString()
            val login = loginET!!.text.toString()
            val password = passwordET!!.text.toString()
            val note = noteET!!.text.toString()
            val date = Date()
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            val dateTime = formatter.format(date)
            if (login == "" || password == "" || title == ""){
                Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
            }
            else {
                val helper = DBHelper(this)
                helper.insertIntoDB(title,login,password,note,dateTime,this)
                dataList.add(Model(title,login,password,note,dateTime))
                rvAdapter.notifyDataSetChanged()
                startActivity(Intent(this, CardsActivity::class.java))
            }
        }



        val generatePasswordButton = findViewById<ImageView>(R.id.generate_password)
        generatePasswordButton.setOnClickListener {
            val length = UserLocalStore(this).returnPasswordLen()
            passwordET?.setText(PasswordGenerator.generatePassword(length))
        }

    }



}
