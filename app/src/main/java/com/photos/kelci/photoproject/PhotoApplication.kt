package com.photos.kelci.photoproject

import android.app.Application
import restclient.VolleyService

class PhotoApplication: Application()  {

    companion object {
        var photoApplication: PhotoApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        photoApplication = this
        VolleyService.setRequestQueue(applicationContext)
    }
}