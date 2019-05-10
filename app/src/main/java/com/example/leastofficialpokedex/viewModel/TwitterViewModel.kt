package com.example.leastofficialpokedex.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.disposables.Disposable

class TwitterViewModel: ViewModel() {
    companion object {
        var disposable: Disposable? = null
    }

    private val myStatuses: MutableLiveData<List<Tweet>> = MutableLiveData()
    val statuses: LiveData<List<Tweet>>
        get() = myStatuses

    private val myError: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = myError

    fun getStatuses(query: String) {
        val apiClient = TwitterCore.getInstance().apiClient
        apiClient.searchService
            .tweets(query, null, null, null,
                null, null, null,
                null, null, null)
            .enqueue(CallBackKt(myStatuses, myError))
    }

    class CallBackKt(private val output: MutableLiveData<List<Tweet>>,
                     private val error: MutableLiveData<String>): Callback<Search>() {
        override fun success(result: Result<Search>?) {
            output.postValue(result?.data?.tweets!!)
        }

        override fun failure(exception: TwitterException?) {
            error.postValue(exception?.localizedMessage)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}