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
        Log.e("TAG", "Created!")
        Log.e("DAGGER", DaggerDaggerComponent.create().getCar().toString())
    }

    override fun onStart() {
        super.onStart()
        Log.e("TAG", "Started!")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("TAG", "Restarted!")
    }

    override fun onResume() {
        super.onResume()
        Log.e("TAG", "Resumed!")
    }

    override fun onPause() {
        super.onPause()
        Log.e("TAG", "Paused!")
    }

    override fun onStop() {
        super.onStop()
        Log.e("TAG", "Stopped!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "Destroyed!")
    }

    fun buttonPressed(view: View) {
        val intent = Intent(this, MyActivity().javaClass)
        startActivity(intent)
    }
}