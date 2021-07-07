package by.senla.timmeleshko

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, StickyService::class.java)
        Log.i("E", "Starting service: ${startService(intent)}")
    }

    fun buttonPressed(view: View) {
        Log.i("E", "Stopping service! Current state: ${stopService(Intent(this, StickyService::class.java))}")
    }
}