package com.photos.kelci.photoproject.view.photo

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.*
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.Nullable
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kelci.familynote.view.base.BaseFragment
import com.photos.kelci.photoproject.PhotoApplication
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.view.helper.DownloadImageService
import com.photos.kelci.photoproject.viewmodel.PhotoDetailViewModel
import android.support.v4.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.fragment_photo.*


class PhotoFragment : BaseFragment(){

    private var rootView : View? = null
    private var detailImage : ImageView? = null
    private var title : String? = ""
    private var image_name : String? = ""
    private var bitmap : Bitmap? = null
    private var downloadImageFromInternet : AsyncTask<String, Void, Bitmap>? = null
    private lateinit var photoDetailViewModel: PhotoDetailViewModel
    private var SERVICE_BOUND = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_photo, container, false)
        detailImage = rootView?.findViewById(R.id.imageView)
        if (savedInstanceState != null) {
            this.title = savedInstanceState.getString("title")
            this.image_name = savedInstanceState.getString("image_name")
        }

        showProgressDialog("loading...")

        photoDetailViewModel = ViewModelProviders.of(activity as FragmentActivity).get(PhotoDetailViewModel::class.java)
        photoDetailViewModel.getPhotoDetail(image_name)

        observeViewModel(photoDetailViewModel)

        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("title", this.title)
        outState.putString("image_name", this.image_name)
    }
    override fun setArguments(args: Bundle?) {
        this.title = args?.getString(PhotoApplication.photoApplication!!.getString(R.string.title))
        this.image_name = args?.getString(PhotoApplication.photoApplication!!.getString(R.string.image_name))
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar!!.title = this.title
        messageReceiver = BiddingServicesMessageReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(DownloadImageService.BROADCAST_ACTION)
        LocalBroadcastManager.getInstance(PhotoApplication.photoApplication!!.applicationContext).registerReceiver(messageReceiver!!, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        if (messageReceiver != null) {
            //to prevent "Receiver not registered" error because sometime the receiver was not registered.
            try {
                LocalBroadcastManager.getInstance(PhotoApplication.photoApplication!!.applicationContext).unregisterReceiver(messageReceiver!!)
            } catch (e: Exception) {
            }

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        downloadImageFromInternet?.cancel(false)
        getMainActivity()?.unbindService(downloadServiceConnection)
    }

    private fun observeViewModel(viewModel : PhotoDetailViewModel){
        viewModel.photoDetailResult.observe(this,  object : Observer<PhotoDetail> {
            override fun onChanged(imageDetail : PhotoDetail?) {
                if (imageDetail?.photographer == null) {
                    showAlertBox("Please check your internet connection or try again later.", "Error loading photo details!")
                } else {
                    author_name.text = imageDetail.photographer
                    date.text = imageDetail.date
                    location.text = imageDetail.location
                    likes.text = imageDetail.likes.toString()
                    filter.text = imageDetail.filter.toString()
                    downloadImage(imageDetail.photoLink)
                }

            }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        downloadImageFromInternet?.cancel(false)
    }

    private fun downloadImage(photoLink : String?) {
        // Bind to LocalService
        var intent = Intent(getMainActivity(), DownloadImageService::class.java)
        intent.putExtra(PhotoApplication.photoApplication!!.getString(R.string.photoLink),photoLink)
        if (SERVICE_BOUND) {
            getMainActivity()?.unbindService(downloadServiceConnection)
        }
        getMainActivity()?.bindService(intent, downloadServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private val downloadServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.i(javaClass.name, "onServiceConnected")
            SERVICE_BOUND = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.i(javaClass.name, "onServiceDisconnected")
            SERVICE_BOUND = false
        }
    }

    private var messageReceiver: BiddingServicesMessageReceiver? = null

    private inner class BiddingServicesMessageReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            dismissProgressDialog()
            val notificationData = intent.extras
            val byteArray = notificationData.getByteArray(DownloadImageService.BROADCAST_PHOTO_KEY)
            val errorMessage = notificationData.getString(DownloadImageService.BROADCAST_MESSAGE_KEY)
            if (errorMessage != null) {
                getMainActivity()?.showAlertBox(errorMessage, "Photo download failed!")
                detailImage?.setImageBitmap(BitmapFactory.decodeResource(getMainActivity()?.resources, R.drawable.sad_face))
                return
            }
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            detailImage?.setImageBitmap(bitmap)
        }
    }
}