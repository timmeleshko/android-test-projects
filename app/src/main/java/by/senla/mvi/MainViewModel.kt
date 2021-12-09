package by.senla.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.senla.mvi.intent.MainIntent
import by.senla.mvi.model.repository.NetworkRepository
import by.senla.mvi.viewstate.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

private const val RX_TAG = "RxJava2"
private const val ERROR_PREFIX = "Error while loading data! Error message:"

class MainViewModel(
    private val repository: NetworkRepository = NetworkRepository()
): ViewModel() {

    val mainIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.NoState)
    val state: StateFlow<MainState> get() = _state

    fun handleIntent() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchHTMLData -> fetchData(it.uri)
                }
            }
        }
    }

    private fun fetchData(uri: String) {
        repository.getData(uri)
            .subscribe(object : Subscriber<List<String>> {
                override fun onSubscribe(s: Subscription?) {
                    Log.e(RX_TAG, "Subscribed!")
                    viewModelScope.launch {
                        _state.value = MainState.Loading
                    }
                    s?.request(1)
                }

                override fun onNext(t: List<String>) {
                    Log.e(RX_TAG, "The next emitted!")
                    viewModelScope.launch {
                        _state.value = MainState.Success(t)
                    }
                }

                override fun onError(t: Throwable?) {
                    t?.printStackTrace()
                    viewModelScope.launch {
                        _state.value = MainState.Error("$ERROR_PREFIX ${t?.localizedMessage ?: "No message."}")
                    }
                }

                override fun onComplete() {
                    Log.e(RX_TAG, "Completed!")
                }
            }
        )
    }
}
