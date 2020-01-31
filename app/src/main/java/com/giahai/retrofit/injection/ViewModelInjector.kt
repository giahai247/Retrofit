package com.giahai.retrofit.injection

import com.giahai.retrofit.ui.PostListViewModel
import dagger.Component
import javax.inject.Singleton

//annotation @Component có chức năng kết nối giữa @Module và các lớp cần sử dụng
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects các Dependency cần thiết vào PostListViewModel.
     * @param postListViewModel PostListViewModel để thêm các phụ thuộc
     */
    fun inject(postListViewModel: PostListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder
    }
}