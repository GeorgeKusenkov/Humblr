package com.example.humblr.data.models.saved.comments

import com.google.gson.annotations.SerializedName


data class SavedCommentsResponse(
    @SerializedName("kind") var kind: String? = null,
    @SerializedName("data") var data: Data? = Data()
)