package com.library.core.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel
constructor(private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()) : ViewModel() {


    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }

    fun getCompositeDisposable(): CompositeDisposable {
        return mCompositeDisposable
    }

    var progressLoading = MutableLiveData<Boolean>()

    open fun setLoading(isLoading: Boolean) {
        progressLoading.value = isLoading
    }

}