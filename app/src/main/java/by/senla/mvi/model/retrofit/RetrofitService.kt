package by.senla.mvi.model.retrofit

import io.reactivex.Flowable
import retrofit2.http.GET

interface RetrofitService {
    @GET("/")
    fun readHTMLData(): Flowable<String>
    // Single может прийти только один onNext, либо OnError. После этого Single считается завершенным.
    // Maybe может прийти либо один onNext, либо onComplete, либо OnError. После этого Maybe считается завершенным.
    // Completable может прийти либо onComplete, либо OnError. После этого Maybe считается завершенным.
}
