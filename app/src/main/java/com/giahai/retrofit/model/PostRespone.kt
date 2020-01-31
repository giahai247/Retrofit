package com.giahai.retrofit.model

import com.google.gson.annotations.SerializedName

data class PostRespone(
    @SerializedName("images")
    var getList: List<Post>
)