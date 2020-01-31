package com.giahai.retrofit.base

import androidx.lifecycle.ViewModel
import com.giahai.retrofit.injection.NetworkModule
import com.giahai.retrofit.injection.ViewModelInjector
import com.giahai.retrofit.ui.PostListViewModel

import com.giahai.retrofit.injection.DaggerViewModelInjector

// sử dụng để tiêm phụ thuộc
abstract class BaseViewModel: ViewModel(){
    //chứa thuộc tính method cơ bản

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()
    init {
        inject()
    }
    private fun inject() {
        when (this) {
            is PostListViewModel -> injector.inject(this)
        }
    }
}