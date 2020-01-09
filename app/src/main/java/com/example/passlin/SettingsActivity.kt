package com.example.passlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val seekBarProgress = findViewById<TextView>(R.id.password_len_seekbar)

        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        seekBar.progress = UserLocalStore(seekBar.context).returnPasswordLen()
        seekBarProgress.text = "Current length is progress ${seekBar.progress}"

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBarProgress.text = "Current length is progress $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                UserLocalStore(seekBar!!.context).storePasswordLen(seekBar.progress)
            }
        })
    }
}