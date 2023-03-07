package com.example.humblr.data.models.saved.links

import com.google.gson.annotations.SerializedName


data class Gildings(
    @SerializedName("gid_1")
    var gid1: Int? = null,

    @SerializedName("gid_2")
    var gid2: Int? = null
)