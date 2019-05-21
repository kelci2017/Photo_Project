package com.photos.kelci.photoproject.view.helper

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.os.Binder
import android.support.v4.content.LocalBroadcastManager
import com.photos.kelci.photoproject.PhotoApplication
import com.photos.kelci.photoproject.R
import java.io.ByteArrayOutputStream


class DoanloadImageService : Service(), Runnable {

    private var url : String? = ""
    private val mBinder = LoadingServicesBinder()

    companion object {
        val BROADCAST_ACTION = "ServiceToActivityAction"
        val BROADCAST_KEY = "ServiceToActivityKey"
    }

    override fun onCreate() {

        super.onCreate()

        val mythread = Thread(this)

        mythread.start()

    }


    override fun run() {

        Looper.prepare()

        try {
            var bimage: Bitmap? = null
            try {
                val `in` = java.net.URL(url).openStream()
                bimage = BitmapFactory.decodeStream(`in`)
                sendMessageToActivity(bimage)
            } catch (e: Exception) {
                Log.e("Error Message", e.message)
                e.printStackTrace()
            }

        } catch (e1: Exception) {

            e1.printStackTrace()

        }

        Looper.loop()

    }

    inner class LoadingServicesBinder : Binder() {
        fun getSerive() : DoanloadImageService {
            return this@DoanloadImageService
        }
    }
    override fun onBind(intent: Intent): IBinder? {

        this.url = intent.getStringExtra(PhotoApplication.photoApplication!!.getString(R.string.photoLink))

        return mBinder

    }

    private fun sendMessageToActivity(bitmap: Bitmap) {
        val bStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream)
        val byteArray = bStream.toByteArray()
        val broadcastIntent = Intent()
        broadcastIntent.action = BROADCAST_ACTION
        broadcastIntent.putExtra(BROADCAST_KEY, byteArray)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(broadcastIntent)
    }

}