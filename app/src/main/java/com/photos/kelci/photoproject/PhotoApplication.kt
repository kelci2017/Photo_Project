package com.photos.kelci.photoproject

import android.app.Application
import android.graphics.Bitmap
import android.support.v4.util.LruCache
import restclient.VolleyService

class PhotoApplication: Application()  {
    private lateinit var memoryCache: LruCache<String, Bitmap>
    companion object {
        var photoApplication: PhotoApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        photoApplication = this
        VolleyService.setRequestQueue(applicationContext)
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        // Use 1/8th of the available memory for this memory cache.
        val cacheSize = maxMemory / 8

        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {

            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.byteCount / 1024
            }
        }
    }

    fun addBitmapToMemoryCache(key: String?, bitmap: Bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap)
        }
    }

    fun getBitmapFromMemCache(key: String?): Bitmap? {
        return memoryCache.get(key)
    }
}