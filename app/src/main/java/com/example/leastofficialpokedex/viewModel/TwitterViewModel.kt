package com.example.leastofficialpokedex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leastofficialpokedex.model.TwitterDatabaseApi
import com.example.leastofficialpokedex.model.TwitterModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TwitterViewModel: ViewModel() {
    companion object {
        val api: TwitterDatabaseApi by lazy { TwitterDatabaseApi.create() }
        var disposable: Disposable? = null
    }

    private val myStatuses: MutableLiveData<List<TwitterModel.Status>> = MutableLiveData()
    val statuses: LiveData<List<TwitterModel.Status>>
        get() = myStatuses

    fun getStatuses(query: String) {
        disposable?.dispose()
        disposable = api.searchTweets(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                result: TwitterModel.PrimaryResponse ->
                myStatuses.postValue(result.statuses)
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}