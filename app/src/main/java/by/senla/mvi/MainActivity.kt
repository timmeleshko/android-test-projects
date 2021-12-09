package by.senla.mvi

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.senla.mvi.intent.MainIntent
import by.senla.mvi.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var notification: TextView
    private lateinit var loading: ProgressBar

    private var adapter: RecyclerViewAdapter = RecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        recyclerView = findViewById(R.id.recyclerView)
        notification = findViewById(R.id.notificationTextView)
        loading = findViewById(R.id.progressBar)
        setupViewModel()
        observeViewModel()
        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val text = editText.text.toString()
            if (text.isNotEmpty()) {
                lifecycleScope.launch {
                    mainViewModel.mainIntent.send(MainIntent.FetchHTMLData(text))
                }
            }
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.handleIntent()
    }

    private fun observeViewModel() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.NoState -> {
                        notification.text = getString(R.string.no_data)
                        toggleVisibility(notification)
                    }
                    is MainState.Loading -> toggleVisibility(loading)
                    is MainState.Success -> {
                        toggleVisibility(recyclerView)
                        adapter.updateItems(it.data)
                    }
                    is MainState.Error -> {
                        notification.text = it.error
                        toggleVisibility(notification)
                    }
                }
            }
        }
    }

    private fun toggleVisibility(view: View) {
        recyclerView.isVisible = false
        notification.isVisible = false
        loading.isVisible = false
        view.isVisible = true
    }
}
