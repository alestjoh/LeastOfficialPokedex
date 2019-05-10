package com.example.leastofficialpokedex.model

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TwitterDatabaseApi {
    @Headers("authorization: " +
            "OAuth oauth_consumer_key=\"5h3Eek09vto2vCoMBxb3DOoMq\"," +
            "oauth_token=\"2736493027-l7eYsZqopEqNJjxm4dZwCj48DXLg3GWsVyrKYIs\"")
    @GET("search/tweets.json")
    fun searchTweets(@Query("q") query: String):
            Observable<TwitterModel.PrimaryResponse>

    companion object {
        fun create(): TwitterDatabaseApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.twitter.com/1.1/")
                .build()

            return retrofit.create(TwitterDatabaseApi::class.java)
        }
    }
}