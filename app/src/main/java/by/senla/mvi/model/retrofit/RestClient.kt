package by.senla.mvi.model.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL = "https://example.com/"

object RestClient {

    private var retrofit: Retrofit? = null

    fun getRetrofitService(): RetrofitService {
        return getClient().create(RetrofitService::class.java)
    }

    private fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit!!
    }
}
