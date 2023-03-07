package com.example.humblr.data.models.saved.links
import com.google.gson.annotations.SerializedName


data class SavedLinksResponse(
    @SerializedName("kind")
    var kind: String? = null,

    @SerializedName("data")
    var data: Data? = Data()
)