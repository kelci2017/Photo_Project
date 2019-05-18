package com.photos.kelci.photoproject

import android.app.Application

class PhotoApplication: Application()  {

    companion object {
        var photoApplication: PhotoApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        photoApplication = this
    }
}