package by.senla.timmeleshko

import android.content.Context
import android.widget.Toast

class LoginViewModel(
    private val loginRepository: LoginRepository
) {

    /**
     * The ViewModel makeLoginRequest function triggers the network request when the user taps, for example, on a button.
     * @param context To display result in UI depending by callback from another thread.
     */
    fun makeLoginRequest(context: Context, username: String, token: String) {
        val jsonBody = "{ username: \"$username\", token: \"$token\"}"
        loginRepository.makeLoginRequest(jsonBody) { result ->
            when(result) {
                is Result.Success<String> -> Toast.makeText(context, "Happy!", Toast.LENGTH_LONG).show()
                else -> Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }
}

