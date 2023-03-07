package com.example.humblr.data.models.friends

import com.google.gson.annotations.SerializedName


data class FriendsResponse (

  @SerializedName("kind" )
  var kind : String? = null,

  @SerializedName("data" )
  var data : Data?   = Data()

)