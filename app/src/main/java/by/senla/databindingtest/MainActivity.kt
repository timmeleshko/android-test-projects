package by.senla.databindingtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import by.senla.databindingtest.databinding.MainActivityBinding
import by.senla.databindingtest.ui.main.MainFragment
import by.senla.databindingtest.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java] // Only for this activity scope.
        val binding: MainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}
