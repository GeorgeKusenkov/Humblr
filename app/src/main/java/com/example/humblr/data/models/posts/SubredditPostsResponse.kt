package com.example.humblr.data.models.posts

import com.google.gson.annotations.SerializedName


data class SubredditPostsResponse(
    @SerializedName("kind")
    var kind: String? = null,

    @SerializedName("data")
    var data: Data? = Data()
)