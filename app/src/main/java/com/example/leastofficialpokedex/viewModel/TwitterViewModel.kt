package com.example.leastofficialpokedex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.leastofficialpokedex.model.TwitterDatabaseApi
import com.example.leastofficialpokedex.model.TwitterModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class TwitterViewModel: ViewModel() {
    companion object {
        val api: TwitterDatabaseApi by lazy { TwitterDatabaseApi.create() }
        var disposable: Disposable? = null
    }

    private val myStatuses: MutableLiveData<List<TwitterModel.Status>> = MutableLiveData()
    val statuses: LiveData<List<TwitterModel.Status>>
        get() = myStatuses

    private val myError: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = myError

    fun getStatuses(query: String) {
        disposable?.dispose()
        disposable = api.searchTweets(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onNext = {
                    myStatuses.postValue(it.statuses)
                },
                onError = {
                    myError.postValue(it.localizedMessage)
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}