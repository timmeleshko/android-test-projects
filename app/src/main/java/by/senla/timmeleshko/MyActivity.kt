package by.senla.timmeleshko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import javax.inject.Inject

class MyActivity : AppCompatActivity() {

    @Inject
    lateinit var networkService: NetworkService // Инжектить только public

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MyApplication).appComponent()
            .inject(this) // Лучше инжектить раньше всего, чтобы инициализация сервиса и контекста прошла до отрисовки
        setContentView(R.layout.activity_my)
        Log.e("TAG", "My: Created! Said ${networkService.someString()}")
    }

    override fun onStart() {
        super.onStart()
        Log.e("TAG", "My: Started!")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("TAG", "My: Restarted!")
    }

    override fun onResume() {
        super.onResume()
        Log.e("TAG", "My: Resumed!")
    }

    override fun onPause() {
        super.onPause()
        Log.e("TAG", "My: Paused!")
    }

    override fun onStop() {
        super.onStop()
        Log.e("TAG", "My: Stopped!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "My: Destroyed!")
    }
}