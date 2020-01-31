package com.giahai.retrofit.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.giahai.retrofit.base.BaseViewModel
import com.giahai.retrofit.model.Post
import com.giahai.retrofit.model.PostDao
import com.giahai.retrofit.network.PostApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject
//nhận kết quả từ API và hiển thị
class PostListViewModel(private val postDao: PostDao): BaseViewModel(){
    //sử dụng dagger 2
    //@Inject để được nhúng các đối tượng phụ thuộc
    @Inject
    lateinit var postApi: PostApi
    val postListAdapter: PostListAdapter = PostListAdapter()
    private lateinit var subscription: Disposable

    //thêm MutableLiveData,
    // MutableLiveData sẽ có thể quan sát để cập nhật khả năng hiển thị của ProgressBar trong khi truy xuất dữ liệu từ API.
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    //thêm thuộc tính MutableLiveData(cho thông báo lỗi) và thuộc tính OnClickListener
    val errorMessage:MutableLiveData<String> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    init{
        loadPosts()
    }

    private fun loadPosts(){
        subscription = Observable.fromCallable { postDao.all }
            .concatMap {
                    dbPostList ->
                if(dbPostList.isEmpty()) {
                    postApi.getPosts().concatMap { apiPostList ->
                        postDao.insertAll(*apiPostList.getList.toTypedArray())
                        Observable.just(apiPostList.getList)
                    }
                }
                else {
                    Observable.just(dbPostList)
                }
            }
                //RX java2 lấy dữ liệu về
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
                { t -> onRetrievePostListError(t) }
            )
    }
    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onRetrievePostListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish(){
        loadingVisibility.value = View.GONE

    }

    private fun onRetrievePostListSuccess(postList:List<Post>){
        postListAdapter.updatePostList(postList)
    }

    private fun onRetrievePostListError(throws: Throwable){
        errorMessage.value = throws.message
    }
}