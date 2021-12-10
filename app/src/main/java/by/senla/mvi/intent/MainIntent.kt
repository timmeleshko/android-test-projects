package by.senla.mvi.intent

sealed class MainIntent {
    data class FetchHTMLData(val uri: String, val find: String? = null) : MainIntent()
    object ShowFind : MainIntent()
}
