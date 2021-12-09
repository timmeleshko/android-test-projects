package by.senla.mvi

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.senla.mvi.intent.MainIntent
import by.senla.mvi.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setupViewModel()
        observeViewModel()
        lifecycleScope.launch {
            mainViewModel.mainIntent.send(MainIntent.FetchHTMLData("https://github.com/"))
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.handleIntent()
    }

    private fun observeViewModel() {
        val tv = findViewById<TextView>(R.id.textView)
        lifecycleScope.launch {
            mainViewModel.state.collect {
                tv.text = when (it) {
                    is MainState.NoState -> ""
                    is MainState.Loading -> "Loading..."
                    is MainState.Success -> it.data.toString()
                    is MainState.Error -> it.error
                }
            }
        }
    }
}
