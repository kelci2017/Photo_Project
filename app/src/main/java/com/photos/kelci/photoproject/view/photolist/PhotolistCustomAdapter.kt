package com.photos.kelci.photoproject.view.photolist

import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.photos.kelci.photoproject.R
import java.util.ArrayList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.photos.kelci.photoproject.PhotoApplication
import com.photos.kelci.photoproject.view.helper.DownloadImageFromInternet
import com.photos.kelci.photoproject.view.photo.PhotoFragment
import com.kelci.familynote.view.base.RootActivity


class PhotolistCustomAdapter(val context : RootActivity?, val items : ArrayList<PhotoListItem>): RecyclerView.Adapter<PhotolistCustomAdapter.ViewHolder>() {

    private var downloadImageFromInternet : AsyncTask<String, Void, Bitmap>? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.title?.text = items[position].title
        val bitmap = PhotoApplication.photoApplication?.getBitmapFromMemCache(items[position].photoId)
        if (bitmap != null) {
            holder?.thumnail?.setImageBitmap(bitmap)
        } else {
            downloadImageFromInternet = DownloadImageFromInternet(items[position].photoId, holder?.thumnail, null, true)
                    .execute(items[position].photoLink)
        }

        holder?.itemView?.setOnClickListener { v ->
            var photoFragment = PhotoFragment() as Fragment

            val fragmentManager = context?.supportFragmentManager
            val bundle = Bundle()
            bundle.putString(PhotoApplication.photoApplication?.resources!!.getString(R.string.title), items[position].title)
            bundle.putString(PhotoApplication.photoApplication?.resources!!.getString(R.string.image_name), items[position].photoId)
            photoFragment.arguments = bundle
            val fragmentTransaction = fragmentManager?.beginTransaction()

            fragmentTransaction?.replace(R.id.fragment, photoFragment, "PhotoFragment")
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.photolist_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val thumnail = itemView.findViewById(R.id.photo_icon) as ImageView
        val title = itemView.findViewById(R.id.photo_disc) as TextView

    }

    fun cancelAsyncTask(){
        downloadImageFromInternet?.cancel(false)
    }
}