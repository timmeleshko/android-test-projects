package by.senla.timmeleshko

import android.app.Application
import android.util.Log

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.e("TAG", "Application: Created!")
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.e("TAG", "Application: Terminated!")
    }
}