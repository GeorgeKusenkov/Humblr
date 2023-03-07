package com.example.humblr.data.models.posts.comments

import com.google.gson.annotations.SerializedName

data class CommentsResponse(
    @SerializedName("kind")
    var kind: String? = null,

    @SerializedName("data")
    var data: Data? = Data()
)