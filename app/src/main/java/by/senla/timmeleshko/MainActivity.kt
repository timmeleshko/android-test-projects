package by.senla.timmeleshko

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dagger.Component
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var networkService: NetworkService // Инжектить только public

    @Inject
    lateinit var appContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).appComponent()
            .inject(this) // Лучше инжектить раньше всего
        setContentView(R.layout.activity_main)
        Log.e("TAG", "Created! Said: $appContext ${networkService.someString()}")
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
}