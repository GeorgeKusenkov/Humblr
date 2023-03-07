package com.example.humblr.data.models.saved.links
import com.google.gson.annotations.SerializedName


data class ResizedStaticIcons(
    @SerializedName("url")
    var url: String? = null,

    @SerializedName("width")
    var width: Int? = null,

    @SerializedName("height")
    var height: Int? = null
)