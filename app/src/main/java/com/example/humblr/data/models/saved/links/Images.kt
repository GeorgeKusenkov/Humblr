package com.example.humblr.data.models.saved.links

import com.google.gson.annotations.SerializedName


data class Images(
    @SerializedName("source")
    var source: Source? = Source(),

    @SerializedName("resolutions")
    var resolutions: ArrayList<Resolutions> = arrayListOf(),

    @SerializedName("variants")
    var variants: Variants? = Variants(),

    @SerializedName("id")
    var id: String? = null
)