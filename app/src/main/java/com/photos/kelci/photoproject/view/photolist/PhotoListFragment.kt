package com.photos.kelci.photoproject.view.photolist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kelci.familynote.view.base.BaseFragment
import com.photos.kelci.photoproject.R
import kotlinx.android.synthetic.main.fragment_photolist.*
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.photos.kelci.photoproject.viewmodel.PhotolistViewModel


class PhotoListFragment : BaseFragment() {

    private var rootView : View? = null
    private var photosList = ArrayList<PhotoListItem>()
    private var photolistAdapter : PhotolistCustomAdapter? = null
    private lateinit var photolistViewModel: PhotolistViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_photolist, container, false)
        val rv = rootView?.findViewById<RecyclerView>(R.id.photolist)
        rv?.layoutManager = LinearLayoutManager(this@PhotoListFragment.context, LinearLayout.VERTICAL, false)

        photolistViewModel = ViewModelProviders.of(activity as FragmentActivity).get(PhotolistViewModel::class.java)
        photolistViewModel.getPhotolist()

        showProgressDialog("loading...")

        observeViewModel(photolistViewModel)

        return rootView
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar!!.title = "Photos"
        if (photolistViewModel.photolistResult.value != null) {
            photolistAdapter = PhotolistCustomAdapter(getMainActivity(), photolistViewModel.photolistResult.value as java.util.ArrayList<PhotoListItem>)
            photolist.adapter = photolistAdapter
            photolistAdapter?.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        photolistAdapter?.cancelAsyncTask()
    }

    private fun observeViewModel(viewModel : PhotolistViewModel){
        viewModel.photolistResult.observe(this,  object : Observer<ArrayList<PhotoListItem>> {
            override fun onChanged(@Nullable baseResult: ArrayList<PhotoListItem>?) {
                dismissProgressDialog()
                if (baseResult != null && baseResult.count() > 0){
                     photosList = baseResult
                    photolistAdapter = PhotolistCustomAdapter(getMainActivity(), photosList)
                    photolist.adapter = photolistAdapter
                    photolistAdapter?.notifyDataSetChanged()
                } else {
                    showAlertBox("Please check your internet connection or try again later.", "Error loading photos list!")
                }
            }
        })
    }
}