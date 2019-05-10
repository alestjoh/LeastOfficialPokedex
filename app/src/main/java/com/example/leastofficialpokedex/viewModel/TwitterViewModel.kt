package com.example.leastofficialpokedex.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leastofficialpokedex.model.TwitterDatabaseApi
import com.example.leastofficialpokedex.model.TwitterModel
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.session.config.account
import jp.nephy.penicillin.core.session.config.application
import jp.nephy.penicillin.core.session.config.token
import jp.nephy.penicillin.endpoints.search
import jp.nephy.penicillin.endpoints.search.search
import jp.nephy.penicillin.endpoints.users
import jp.nephy.penicillin.endpoints.users.search
import jp.nephy.penicillin.extensions.complete
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

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

    fun getStatusesPenicillin(query: String) {
        viewModelScope.launch {
            runCatching {
                val client = PenicillinClient {
                    account {
                        application("5h3Eek09vto2vCoMBxb3DOoMq",
                            "KGDT8EupGgsX91fUEd1q8sPuCYIRlDwLuRIHzg9oqPqNXA65pl")
                        token("2736493027-l7eYsZqopEqNJjxm4dZwCj48DXLg3GWsVyrKYIs",
                            "wy8kAZaPTTX4ZM728jSMmKwfTMO8EhYT5ygFi1lhbJdT8")
                    }
                }
                client.search.search(query).complete()
                //client.users.search(query).complete()
            }.onFailure {
                myError.postValue(it.message)
            }.onSuccess {
                myError.postValue(it.result.statuses.size.toString())
            }
        }
    }

    fun getStatusesTwitterKit(query: String) {
        val apiClient = TwitterCore.getInstance().getApiClient()
        val statusesService = apiClient.statusesService
        statusesService.show(524971209851543553L,
            null, null, null)
            .enqueue(CallBackKt(myError))
    }

    class CallBackKt(val output: MutableLiveData<String>): Callback<Tweet>() {
        override fun success(result: Result<Tweet>?) {
            output.postValue(result?.data?.text)
        }

        override fun failure(exception: TwitterException?) {
            output.postValue(exception?.localizedMessage)
        }
    }

//    private class getStatusesOperation: AsyncTask<ApiClient, Void?, Void?>() {
//        override fun doInBackground(vararg params: ApiClient?): Void? {
//            val session = params[0]
//            session?.users?.search("bulbasaur").await().forEach {
//                Log.d("TwitterViewModel", it.toString())
//            }
//
//            return null
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}