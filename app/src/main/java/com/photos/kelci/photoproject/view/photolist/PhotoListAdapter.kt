package com.photos.kelci.photoproject.view.photolist

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.photos.kelci.photoproject.PhotoApplication
import com.photos.kelci.photoproject.R
import java.util.ArrayList
import com.photos.kelci.photoproject.view.helper.DownloadImageFromInternet


class PhotoListAdapter(context : Context, items : ArrayList<PhotoListItem>) : BaseAdapter() {

    private var context : Context = context
    private var items : ArrayList<PhotoListItem> = items
    private var downloadImageFromInternet : AsyncTask<String, Void, Bitmap>? = null

    override fun getCount(): Int {
        return items.count()
    }

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var convertview = p1
        convertview = inflater.inflate(R.layout.photolist_item, p2, false)

        val thumnail = convertview.findViewById(R.id.photo_icon) as ImageView
        val title = convertview.findViewById(R.id.photo_disc) as TextView

        title.text = items[p0].title

        val thumnailString = PhotoApplication.photoApplication!!.getString(R.string.thumnail)
        val serverURL = PhotoApplication.photoApplication!!.getString(R.string.server_url)

        val bitmap = PhotoApplication.photoApplication?.getBitmapFromMemCache(items[p0].photoLink)
        if (bitmap != null) {
            thumnail.setImageBitmap(bitmap)
        } else {
            downloadImageFromInternet = DownloadImageFromInternet(items[p0].photoLink, thumnail, null, true)
                    .execute(String.format(thumnailString, serverURL, items[p0].photoLink))
        }

        return convertview
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    fun cancelAsyncTask(){
        downloadImageFromInternet?.cancel(false)
    }
}