package by.senla.mvi.model.repository

import by.senla.mvi.model.retrofit.RestClient
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NetworkRepository {

    fun getData(uri: String): Flowable<List<String>> {
        val callback = RestClient.getRetrofitService(uri).readHTMLData()
        return callback
            .buffer(2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
