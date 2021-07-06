package by.senla.timmeleshko

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @author https://developer.android.com/guide/background/threading#kotlin
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* A Looper is an object that runs the message loop for an associated thread. Once you've
        created a Handler, you can then use the post(Runnable) method to run a block of code in the corresponding thread.
         */
        val executorService: ExecutorService = Executors.newFixedThreadPool(4)
        val mainThreadHandler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())
        val loginRepository = LoginRepository(executorService, mainThreadHandler)
        val loginViewModel = LoginViewModel(loginRepository)
        loginViewModel.makeLoginRequest(this, "hello", "world")
    }
}