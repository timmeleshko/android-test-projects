package by.senla.timmeleshko

import android.content.Context

class NetworkServiceImpl(
    private val context: Context
): NetworkService {

    override fun someString(): String {
        return context.packageName
    }

    override fun appContext(): Context {
        return context
    }
}