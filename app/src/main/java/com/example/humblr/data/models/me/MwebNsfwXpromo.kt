package com.example.humblr.data.models.me

import com.google.gson.annotations.SerializedName


data class MwebNsfwXpromo(
    @SerializedName("owner")
    var owner: String? = null,

    @SerializedName("variant")
    var variant: String? = null,

    @SerializedName("eфxperiment_id")
    var experimentId: Int? = null
)