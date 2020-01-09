package com.example.passlin


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.view.Menu
import android.view.MenuItem

class CardsActivity : AppCompatActivity() {
    companion object {
        lateinit var dataList: ArrayList<Model>
        lateinit var rvAdapter: RVAdapter

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cards)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, CardActivity::class.java))
        }
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && floatingActionButton.visibility == View.VISIBLE) {
                    floatingActionButton.hide()
                } else if (dy < 0 && floatingActionButton.visibility != View.VISIBLE) {
                    floatingActionButton.show()
                }
            }
        })





        val helper = DBHelper(this)
        dataList = helper.getDataFromDB(this)

//      pass the values to RvAdapter
        rvAdapter = RVAdapter(dataList)
//      set the recyclerView to the adapter


        recyclerView.adapter = rvAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()}
            R.id.tips -> {Toast.makeText(this, "Tips", Toast.LENGTH_LONG).show()}
        }
        return super.onOptionsItemSelected(item)
    }
}



