package com.photos.kelci.photoproject.view.helper

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView

class DownloadImageFromInternet (var imageView: ImageView?, var progressDialog : ProgressDialog?) : AsyncTask<String, Void, Bitmap>() {

    override fun doInBackground(vararg urls: String): Bitmap? {
        val imageURL = urls[0]
        var bimage: Bitmap? = null
        try {
            val `in` = java.net.URL(imageURL).openStream()
            bimage = BitmapFactory.decodeStream(`in`)

        } catch (e: Exception) {
            Log.e("Error Message", e.message)
            e.printStackTrace()
        }

        return bimage
    }

    override fun onPostExecute(result: Bitmap) {
        imageView?.setImageBitmap(result)
        try {
            if (progressDialog != null || progressDialog!!.isShowing) {
                progressDialog?.dismiss()
            }
        } catch (e: Exception) {
            return
        }
    }
}