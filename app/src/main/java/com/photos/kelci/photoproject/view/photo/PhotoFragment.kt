package com.photos.kelci.photoproject.view.photo

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kelci.familynote.view.base.BaseFragment
import com.photos.kelci.photoproject.PhotoApplication
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.model.datastructure.ImageDetail
import com.photos.kelci.photoproject.view.helper.DownloadImageFromInternet
import com.photos.kelci.photoproject.viewmodel.PhotoDetailViewModel
import kotlinx.android.synthetic.main.fragment_photo.*
import android.os.AsyncTask.execute
import android.os.AsyncTask.execute





class PhotoFragment : BaseFragment(){

    private var rootView : View? = null
    private var detailImage : ImageView? = null
    var title : String? = ""
    var image_name : String? = ""
    private var progressDialog: ProgressDialog? = null
    private var downloadImageFromInternet : AsyncTask<String, Void, Bitmap>? = null
    private lateinit var photoDetailViewModel: PhotoDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_photo, container, false)
        detailImage = rootView?.findViewById(R.id.imageView)

        val photoDetailString = PhotoApplication.photoApplication!!.getString(R.string.detailphoto)
        val serverURL = PhotoApplication.photoApplication!!.getString(R.string.server_url)

        showProgressDialog()

//        val bitmap = PhotoApplication.photoApplication?.getBitmapFromMemCache(this.name)
//        if (bitmap != null) {
//            detailImage?.setImageBitmap(bitmap)
//        } else {
//            downloadImageFromInternet = DownloadImageFromInternet(this.name, detailImage, progressDialog)
//                    .execute(String.format(photoDetailString, serverURL, this.image_name))
//        }

        downloadImageFromInternet = DownloadImageFromInternet(this.name,detailImage, progressDialog, false)
                .execute(String.format(photoDetailString, serverURL, this.image_name))

        photoDetailViewModel = ViewModelProviders.of(activity as FragmentActivity).get(PhotoDetailViewModel::class.java)
        photoDetailViewModel.getPhotoDetail(image_name)

        observeViewModel(photoDetailViewModel)

        return rootView
    }

    override fun setArguments(args: Bundle?) {
        this.title = args?.getString(PhotoApplication.photoApplication!!.getString(R.string.title))
        this.image_name = args?.getString(PhotoApplication.photoApplication!!.getString(R.string.image_name))
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar!!.title = this.title

    }

    override fun onDestroyView() {
        super.onDestroyView()
        downloadImageFromInternet?.cancel(false)
    }

    private fun showProgressDialog(){
        if (progressDialog == null) {

            try {
                progressDialog = ProgressDialog.show(getMainActivity(), "", "loading...", false)
                progressDialog?.show()
            } catch (e: Exception) {
            }

        }
    }
    private fun observeViewModel(viewModel : PhotoDetailViewModel){
        viewModel.photoDetailResult.observe(this,  object : Observer<ImageDetail> {
            override fun onChanged(@Nullable imageDetail : ImageDetail?) {
                if (imageDetail?.photographer == null) {
                    showAlertBox("Please check your internet connection or try again later.", "Error loading photo details!")
                } else {
                    author_name.text = imageDetail?.photographer
                    date.text = imageDetail?.date
                    location.text = imageDetail?.location
                    likes.text = imageDetail?.likes.toString()
                    filter.text = imageDetail?.filter.toString()
                }

            }
        })
    }
}