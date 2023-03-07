package com.example.humblr.data.models.subreddit

import com.google.gson.annotations.SerializedName


data class SubredditsResponse (

  @SerializedName("kind" ) var kind : String? = null,
  @SerializedName("data" ) var data : Data?   = Data()

)