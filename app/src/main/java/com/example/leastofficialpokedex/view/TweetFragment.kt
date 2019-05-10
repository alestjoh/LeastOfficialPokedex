package com.example.leastofficialpokedex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.leastofficialpokedex.R
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.fragment_tweet.*

class TweetFragment(private val tweet: Tweet) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        tv_text_tweetFragment.text = tweet.text
        tv_annotation_tweetFragment.text = getString(R.string.dex_entry_provided_by).format(tweet.user.name)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tweet, container, false)
    }


}
