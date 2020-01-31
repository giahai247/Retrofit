package com.giahai.retrofit.ui

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.giahai.retrofit.R
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import kotlinx.android.synthetic.main.activity_post_list.*
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.activity_view_image.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ViewImageActivity : AppCompatActivity() {
    lateinit var fab_label: FloatingActionMenu
    lateinit var fabShare: FloatingActionButton
    lateinit var fabWallpaper: FloatingActionButton
    lateinit var fabDownload: FloatingActionButton
    private val PERMISSION_REQUEST_CODE : Int = 1000
    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)
        val intent = intent
        val link: String = intent.getStringExtra("link")
        Glide.with(this).load(link).into(imgAss)
        initFloat()
        fabShare.setOnClickListener {
            shareImage()
        }
        fabDownload.setOnClickListener {

            startDownload(link)
        }

        fabWallpaper.setOnClickListener {
            dialogWallpaper()
        }
    }
    fun dialogWallpaper(){
        val builder = AlertDialog.Builder(this@ViewImageActivity)
        builder.setTitle("Đặt hình nền")
        builder.setMessage("Bạn có muốn đặt hình nền không?")
        builder.setCancelable(false)
        builder.setPositiveButton(
            "OK"
        ) { dialogInterface, i ->
            setWallpaper()
            dialogInterface.dismiss()
        }
        builder.setNegativeButton(
            "Cancle"
        ) { dialogInterface, i -> dialogInterface.dismiss() }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun setWallpaper(){
        val intent = intent
        val link: String = intent.getStringExtra("link")

        Glide.with(this).load(link)
            .into(object : SimpleTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable?,
                    transition: Transition<in Drawable?>?
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                        imgAss.setBackground(resource)
                    }
                }
            })


        Toast.makeText(applicationContext,"Thành Công",Toast.LENGTH_SHORT).show()
    }



    fun shareImage(){
        intent = intent
        val link: String = intent.getStringExtra("link")
        Glide.with(this).load(link).into(imgAss)
        val intent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_STREAM, link)
            this.type = "image/jpeg"
        }
        startActivity(Intent.createChooser(intent, resources.getText(R.string.send_to)))
    }

    private fun initFloat(){
        fabShare = findViewById(R.id.fabShare);
        fabWallpaper = findViewById(R.id.fabWallpaper);
        fabDownload = findViewById(R.id.fabDownload);
        fab_label = findViewById(R.id.fab_label)
    }


    private fun startDownload(url: String){
        Toast.makeText(this,"Downloading..", Toast.LENGTH_SHORT).show()
        val request : DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI or
                    DownloadManager.Request.NETWORK_MOBILE)
            .setTitle("Download")
            .setDescription("GalleryApp")
            .setMimeType("image/jpg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        val downloadManager : DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) Toast.makeText(
                    this,
                    "Permission Granted",
                    Toast.LENGTH_SHORT
                ).show() else Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
