package com.example.horizontal_calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.horizontalcalendar.DateItemClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DateItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        horizontal_calendar.initialize(this)
    }

    override fun onDateClick(date: String) {
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show()
    }

}
