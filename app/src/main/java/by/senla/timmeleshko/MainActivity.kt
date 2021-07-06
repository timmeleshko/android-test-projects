package by.senla.timmeleshko

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {}
        override fun onServiceDisconnected(className: ComponentName) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, StickyService::class.java)
        Log.i("E", "Starting service: ${startService(intent)}")
        Log.i("E", "Binding service: ${bindService(intent, mServiceConnection, BIND_AUTO_CREATE)}")
    }

    fun buttonPressed(view: View) {
        Log.i("E", "Unbinding service! Current state: ${unbindService(mServiceConnection)}")
    }
}