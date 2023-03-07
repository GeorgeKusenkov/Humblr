package com.example.humblr.data.models.saved.comments

import com.google.gson.annotations.SerializedName


data class Gildings(
    @SerializedName("subreddit_id") var subredditId: String? = null
)