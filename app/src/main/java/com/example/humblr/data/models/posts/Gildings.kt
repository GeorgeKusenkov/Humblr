package com.example.humblr.data.models.posts

import com.google.gson.annotations.SerializedName


data class Gildings(
    @SerializedName("after")
    var after: String? = null,
)