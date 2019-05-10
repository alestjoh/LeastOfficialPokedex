package com.example.leastofficialpokedex.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.twitter.sdk.android.core.models.Tweet

class TweetPagerAdapter(fragmentManager: FragmentManager,
                        private val tweets: List<Tweet>):
        FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return TweetFragment(tweets[position])
    }

    override fun getCount(): Int = tweets.size
}