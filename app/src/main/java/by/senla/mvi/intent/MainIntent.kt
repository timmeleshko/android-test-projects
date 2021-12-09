package by.senla.mvi.intent

sealed class MainIntent {
    data class FetchHTMLData(val uri: String) : MainIntent()
}
