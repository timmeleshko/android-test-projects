package by.senla.mvi.model.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface RetrofitService {
    @Streaming
    @GET
    fun readHTMLData(@Url uri: String): Call<ResponseBody>
    // Single может прийти только один onNext, либо OnError. После этого Single считается завершенным.
    // Maybe может прийти либо один onNext, либо onComplete, либо OnError. После этого Maybe считается завершенным.
    // Completable может прийти либо onComplete, либо OnError. После этого Maybe считается завершенным.
}
