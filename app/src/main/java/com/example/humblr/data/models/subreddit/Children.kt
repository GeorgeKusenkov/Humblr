package com.example.humblr.data.models.subreddit

import com.google.gson.annotations.SerializedName


data class Children (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : SubredditData?   = SubredditData()

)