package by.senla.mvi.viewstate

sealed class MainState {
    object NoState : MainState()
    object Loading : MainState()
    object Finding : MainState()
    data class Success(val data: List<String>) : MainState()
    data class Error(val error: String?) : MainState()
}
