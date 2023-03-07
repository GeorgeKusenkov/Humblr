package com.example.humblr.data.models.saved.links
import com.google.gson.annotations.SerializedName


data class SavedLink(
    @SerializedName("kind")
    var kind: String? = null,

    @SerializedName("data")
    var linkData: LinkData? = LinkData()
)