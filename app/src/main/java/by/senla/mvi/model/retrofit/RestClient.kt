package by.senla.mvi.model.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RestClient {

    private var retrofit: Retrofit? = null

    fun getRetrofitService(uri: String): RetrofitService {
        return getClient(uri).create(RetrofitService::class.java)
    }

    private fun getClient(uri: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(uri)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit!!
    }
}
