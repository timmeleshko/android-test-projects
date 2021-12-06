package by.senla.databindingtest.model

import androidx.lifecycle.MutableLiveData

object Repository {
    class A(
        var word: MutableLiveData<String> = MutableLiveData("Hello!")
    )
}
