package com.giahai.retrofit.ui

import androidx.lifecycle.MutableLiveData
import com.giahai.retrofit.base.BaseViewModel
import com.giahai.retrofit.model.Post

class PostViewModel: BaseViewModel() {

    // hiển thị danh sách các ảnh.
    // tạo một ViewModel cho từng mục của danh sách.

    private val postTitle = MutableLiveData<String>()
    fun bind(post: Post) {
        postTitle.value = post.url
    }

    fun getPostTitle(): MutableLiveData<String> {
        return postTitle
    }
}