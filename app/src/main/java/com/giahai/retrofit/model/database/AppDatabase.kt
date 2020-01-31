package com.giahai.retrofit.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.giahai.retrofit.model.Post
import com.giahai.retrofit.model.PostDao
// Room: thư viện sinh ra nhằm cung cấp một lớp trừu tượng hóa trên nền của SQLite để thao tác dễ dàng hơn và mạnh mẽ hơn
@Database(entities = arrayOf(Post::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}