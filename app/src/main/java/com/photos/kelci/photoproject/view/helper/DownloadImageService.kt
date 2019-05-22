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


class DownloadImageService : Service(), Runnable {

    private var url : String? = ""
    private val mBinder = LoadingServicesBinder()

    companion object {
        val BROADCAST_ACTION = "ServiceDownloadAction"
        val BROADCAST_PHOTO_KEY = "ServiceDownloadPhotoKey"
        val BROADCAST_MESSAGE_KEY = "ServiceDownloadMessageKey"
    }

    override fun onCreate() {

        super.onCreate()

        Log.i("&&&&&&&&&&&", url)

        val mythread = Thread(this)

        mythread.start()

    }


    override fun run() {
        Looper.prepare()
        try {
            Log.i("the image url is: ", url)
            var bimage: Bitmap? = null
            try {
                val `in` = java.net.URL(url).openStream()
                bimage = BitmapFactory.decodeStream(`in`)
                sendMessageToActivity(bimage, null)
            } catch (e: Exception) {
                /*
                  there maybe exception while downloading from internet
                  if error downloading image, try again
                 */
                try {
                    val `in` = java.net.URL(url).openStream()
                    bimage = BitmapFactory.decodeStream(`in`)
                    sendMessageToActivity(bimage, null)
                } catch (e: Exception) {
                    // if tried second time and caught error again, broadcast error message
                    Log.e("Error Message", e.message)
                    e.printStackTrace()
                    sendMessageToActivity(null, "Error downloading photo from internet, please try again later.")
                }
            }
        } catch (e1: Exception) {

            e1.printStackTrace()

        }

        Looper.loop()

    }

    inner class LoadingServicesBinder : Binder() {
        fun getSerive() : DownloadImageService {
            return this@DownloadImageService
        }
    }
    override fun onBind(intent: Intent): IBinder? {

        this.url = intent.getStringExtra(PhotoApplication.photoApplication!!.getString(R.string.photoLink))
        Log.i("&&&&&&&&&&& ", url)

        return mBinder

    }

    private fun sendMessageToActivity(bitmap: Bitmap?, errorMessage : String?) {
        val bStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, bStream)
        val byteArray = bStream.toByteArray()
        val broadcastIntent = Intent()
        broadcastIntent.action = BROADCAST_ACTION
        broadcastIntent.putExtra(BROADCAST_PHOTO_KEY, byteArray)
        broadcastIntent.putExtra(BROADCAST_MESSAGE_KEY, errorMessage)
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(broadcastIntent)
    }

}