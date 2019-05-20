package com.photos.kelci.photoproject.view.photolist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.kelci.familynote.view.base.BaseFragment
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.view.photo.PhotoFragment
import kotlinx.android.synthetic.main.fragment_photolist.*
import android.support.v7.app.AppCompatActivity
import com.photos.kelci.photoproject.viewmodel.PhotolistViewModel


class PhotoListFragment : BaseFragment() {

    private var rootView : View? = null
    private var photosList = ArrayList<PhotoListItem>()
    private var photolistAdapter : PhotoListAdapter? = null
    private lateinit var photolistViewModel: PhotolistViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_photolist, container, false)

        photolistViewModel = ViewModelProviders.of(activity as FragmentActivity).get(PhotolistViewModel::class.java)
        photolistViewModel.getPhotolist()

        showProgressDialog("loading...")

        observeViewModel(photolistViewModel)

        return rootView
    }

    override fun onStart() {
        super.onStart()
        setListOnClickListener()
        (activity as AppCompatActivity).supportActionBar!!.title = "Photos"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        photolistAdapter?.cancelAsyncTask()
    }

    private fun setListOnClickListener() {

        photolist?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val selectedItem = parent.getItemAtPosition(position) as PhotoListItem

            var photoFragment = PhotoFragment() as Fragment

            val fragmentManager = activity!!.supportFragmentManager
            val bundle = Bundle()
            bundle.putString(getMainActivity()?.resources!!.getString(R.string.title), selectedItem.title)
            bundle.putString(getMainActivity()?.resources!!.getString(R.string.image_name), selectedItem.photoLink)
            photoFragment.arguments = bundle
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.fragment, photoFragment, "PhotoFragment")
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    private fun observeViewModel(viewModel : PhotolistViewModel){
        viewModel.photolistResult.observe(this,  object : Observer<ArrayList<PhotoListItem>> {
            override fun onChanged(@Nullable baseResult: ArrayList<PhotoListItem>?) {
                dismissProgressDialog()
                if (baseResult != null && baseResult.count() > 0){
                     photosList = baseResult
                    photolistAdapter = PhotoListAdapter(this@PhotoListFragment.context!!, photosList)
                    photolist.adapter = photolistAdapter
                    photolistAdapter?.notifyDataSetChanged()
                } else {
                    showAlertBox("Please check your internet connection or try again later.", "Error loading photos list!")
                }
            }
        })
    }
}