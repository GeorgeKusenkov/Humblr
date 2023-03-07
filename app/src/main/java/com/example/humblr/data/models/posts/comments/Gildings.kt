package com.example.humblr.data.models.posts.comments

import com.google.gson.annotations.SerializedName


data class Gildings(
    @SerializedName("kind")
    var kind: String? = null,
)