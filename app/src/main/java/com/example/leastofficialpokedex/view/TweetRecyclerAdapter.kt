package com.example.leastofficialpokedex.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.leastofficialpokedex.R
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.tweet_recycler_item_layout.view.*

class TweetRecyclerAdapter(private val tweets: List<Tweet>):
        RecyclerView.Adapter<TweetRecyclerAdapter.TweetViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tweet_recycler_item_layout, parent, false)

        return TweetViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.textEntry.text = tweets[position].text
        holder.annotation.text = holder.view.context.getString(R.string.dex_entry_provided_by)
            .format(tweets[position].user.name)
    }

    override fun getItemCount(): Int = tweets.size

    class TweetViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val textEntry: TextView = view.tv_text_tweetFragment
        val annotation: TextView = view.tv_annotation_tweetFragment
    }
}