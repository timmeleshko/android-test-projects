package by.senla.mvi

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.senla.mvi.databinding.MainActivityBinding
import by.senla.mvi.databinding.MainFindBinding
import by.senla.mvi.databinding.MainNotificationsBinding
import by.senla.mvi.intent.MainIntent
import by.senla.mvi.viewstate.MainState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var included: MainNotificationsBinding
    private lateinit var includedFind: MainFindBinding
    private lateinit var mainViewModel: MainViewModel

    private var adapter: RecyclerViewAdapter = RecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        included = binding.included
        includedFind = binding.includedFind
        setContentView(binding.root)
        setupViewModel()
        observeViewModel()
        setupViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_find -> {
                lifecycleScope.launch {
                    mainViewModel.mainIntent.send(MainIntent.ShowFind)
                }
            }
        }
        return true
    }

    private fun setupViews() {
        binding.button.setOnClickListener {
            val text = binding.editText.text.toString()
            if (text.isNotEmpty()) {
                lifecycleScope.launch {
                    mainViewModel.mainIntent.send(MainIntent.FetchHTMLData(text))
                }
            }
        }
        includedFind.buttonFindNow.setOnClickListener {
            val uri = binding.editText.text.toString()
            val text = includedFind.findEditText.text.toString()
            if (uri.isNotEmpty() && text.isNotEmpty()) {
                lifecycleScope.launch {
                    mainViewModel.mainIntent.send(MainIntent.FetchHTMLData(uri, text))
                }
            }
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.handleIntent()
    }

    private fun observeViewModel() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.NoState -> {
                        included.notificationTextView.text = getString(R.string.no_data)
                        toggleVisibility(included.notificationTextView)
                    }
                    is MainState.Loading -> toggleVisibility(binding.progressBar)
                    is MainState.Success -> {
                        toggleVisibility(binding.recyclerView)
                        adapter.updateItems(it.data)
                    }
                    is MainState.Error -> {
                        included.errorTextView.text = it.error
                        toggleVisibility(included.errorTextView)
                    }
                    is MainState.Finding -> {
                        toggleVisibility(includedFind.root)
                    }
                }
            }
        }
    }

    private fun toggleVisibility(view: View) {
        binding.recyclerView.isVisible = false
        included.notificationTextView.isVisible = false
        included.errorTextView.isVisible = false
        binding.progressBar.isVisible = false
        includedFind.root.isVisible = false
        view.isVisible = true
    }
}
