package by.senla.mvi.model.repository

import by.senla.mvi.model.retrofit.RestClient
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class NetworkRepository {

    fun getData(uri: String, find: String?): Flowable<List<String>> {
        val callback = RestClient.getRetrofitService().readHTMLData(uri)
        val flowable = Flowable.create<List<String>> ({ subscriber ->
            callback.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    var stringArray = processResponse(response.body())
                    find?.let { stringArray = stringArray.filter { sa -> sa.contains(find, true) } }
                    subscriber.onNext(stringArray)
                    subscriber.onComplete()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    subscriber.onError(t)
                }
            })
        }, BackpressureStrategy.BUFFER)
        return flowable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun processResponse(body: ResponseBody?): List<String> {
        if (body != null) {
            var bufferedReader: BufferedReader? = null
            try {
                val stringList = mutableListOf<String>()
                bufferedReader = BufferedReader(
                    InputStreamReader(body.byteStream())
                ).also {
                    it.use { reader ->
                        var line = ""
                        while (reader.readLine()?.also { rdr -> line = rdr } != null) {
                            stringList.add(line)
                        }
                    }
                }
                return stringList
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return listOf()
    }
}
