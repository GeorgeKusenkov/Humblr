package com.example.humblr.data.models.posts.comments

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("after")
    var after: String? = null,

    @SerializedName("dist")
    var dist: String? = null,

    @SerializedName("modhash")
    var modhash: String? = null,

    @SerializedName("geo_filter")
    var geoFilter: String? = null,

    @SerializedName("children")
    var children: ArrayList<Comment> = arrayListOf(),

    @SerializedName("before")
    var before: String? = null
)