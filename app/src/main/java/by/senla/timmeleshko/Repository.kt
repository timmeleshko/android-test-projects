package by.senla.timmeleshko

import android.os.Handler
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

/**
 * Following the principles of dependency injection, LoginRepository takes an instance of
 * Executor as opposed to ExecutorService because it's executing code and not managing threads.
 * @param resultHandler It's a good practice to inject the handler to the Repository, as it gives you more
 * flexibility. For example, in the future you might want to pass in a different Handler to schedule
 * tasks on a separate thread. If you're always communicating back to the same thread, you can pass the
 * Handler into the Repository constructor, as shown in the following example.
 */
class LoginRepository(
    private val executor: Executor,
    private val resultHandler: Handler
) {

    private val loginUrl = "https://example.com/login"

    /**
     * Function that makes the network request, blocking the current thread. It's moves the
     * execution to the background thread and ignores the response for now.
     * @param callback Callback with the result is called whenever the network request completes or a
     * failure occurs. In Kotlin, we can use a higher-order function.
     */
    fun makeLoginRequest(
        jsonBody: String,
        callback: (Result<String>) -> Unit
    ) {
        /* Inside the execute() we create a new Runnable with the block of code we want to execute in the
        background thread—in our case, the synchronous network request method. Internally,
        the ExecutorService manages the Runnable and executes it in an available thread.
         */
        executor.execute {
            try {
                val response = makeSynchronousLoginRequest(jsonBody)
                resultHandler.post { callback(response) }
            } catch (e: Exception) {
                val errorResult = Result.Error(e)
                resultHandler.post { callback(errorResult) }
            }
        }
    }

    /**
     * HttpURLConnection logic.
     */
    private fun makeSynchronousLoginRequest(jsonBody: String): Result<String> {
        val url = URL(loginUrl)
        (url.openConnection() as? HttpURLConnection)?.run {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json; charset=utf-8")
            setRequestProperty("Accept", "application/json")
            doOutput = true
            outputStream.write(jsonBody.toByteArray())
            return Result.Success("data")
        }
        return Result.Error(Exception("Cannot open HttpURLConnection"))
    }
}
