package com.giahai.retrofit.network

import io.reactivex.Observable
import com.giahai.retrofit.model.PostRespone
import com.giahai.retrofit.model.Post
import retrofit2.http.GET

interface PostApi {
// truy xuất các dữ liệu
    @GET("api/v1/images/search?query=l")
    fun getPosts(): Observable<PostRespone>
}