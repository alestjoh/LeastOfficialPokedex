package com.example.leastofficialpokedex

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig

class CustomApp: Application() {
    override fun onCreate() {
        super.onCreate()
        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig(
                "5h3Eek09vto2vCoMBxb3DOoMq",
                "KGDT8EupGgsX91fUEd1q8sPuCYIRlDwLuRIHzg9oqPqNXA65pl"))
            .debug(true)
            .build()
        Twitter.initialize(config)
    }
}