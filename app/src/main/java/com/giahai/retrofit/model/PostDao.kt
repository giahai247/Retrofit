package com.giahai.retrofit.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
//thêm một lớp DAO để cho phép chèn và lấy Post từ cơ sở dữ liệu.
//Class này cho phép viết những hàm trừu tượng để thêm, sửa, xóa dữ liệu có trong bảng bằng các hàm với các annotation có sẵn của Room
@Dao
interface PostDao {
    @get:Query("SELECT * FROM post")
    val all: List<Post>
    @Insert
    fun insertAll(vararg posts: Post)
}