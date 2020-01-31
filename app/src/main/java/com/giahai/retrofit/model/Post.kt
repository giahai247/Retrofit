package com.giahai.retrofit.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
//gói mô hình của ứng dụng để lưu trữ
//Class này cho phép bạn tạo các trường có tên như nào thì bảng sau này bạn lưu vào cũng sẽ như vậy.
@Entity
class Post
{
    //cập nhật lớp Post để biến nó thành @Entity có thể được lưu trong Cơ sở dữ liệu:
    @field:PrimaryKey
    @SerializedName("id")
    var id: Int= 0
    @SerializedName("url")
    var url: String=""
    @SerializedName("large_url")
    var large_url: String=""
        get() = this.url

    @SerializedName("source_id")
    var source_id: String=""
        get() ="1"
    @SerializedName("site")
    var site: String=""
        get() = "1"
    @SerializedName("copyright")
    var copyright: String =""
        get() = "1"
}