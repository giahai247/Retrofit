package com.giahai.retrofit.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.giahai.retrofit.model.database.AppDatabase
import com.giahai.retrofit.ui.PostListViewModel
//ViewModelFactory để giúp tự động tạo ViewModel trên Activity hoặc Fragment
//ViewModelFactory có một danh sách các provider để có thể taọ bất kì ViewModel nào bị ràng buộc
class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostListViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "posts").build()
            @Suppress("UNCHECKED_CAST")
            return PostListViewModel(db.postDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}