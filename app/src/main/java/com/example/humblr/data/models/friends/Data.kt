package com.example.humblr.data.models.friends

import com.google.gson.annotations.SerializedName


data class Data(
    @SerializedName("children")
    var friends: ArrayList<Friend> = arrayListOf()
)