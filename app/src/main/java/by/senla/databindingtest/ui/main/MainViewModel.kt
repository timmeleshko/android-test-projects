package by.senla.databindingtest.ui.main

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import by.senla.databindingtest.model.Repository

class MainViewModel : ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()
    @Bindable
    var word: LiveData<String>

    init {
        val result = Repository.A().word
        word = Transformations.map(result) { r -> r }
    }

    override fun addOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    fun setUserName(name: String) {
        val result = Repository.A()
        result.word.value = name
        word = Transformations.map(result.word) { r -> r }
        notifyPropertyChanged(BR.word)
    }
}
