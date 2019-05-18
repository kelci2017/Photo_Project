package com.photos.kelci.photoproject.view.photolist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.kelci.familynote.view.base.BaseFragment
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.view.photo.PhotoFragment
import kotlinx.android.synthetic.main.fragment_photolist.*
import android.support.v7.app.AppCompatActivity
import com.kelci.familynote.view.base.RootActivity
import com.photos.kelci.photoproject.model.datastructure.BaseResult
import com.photos.kelci.photoproject.viewmodel.PhotolistViewModel


class PhotoListFragment : BaseFragment() {

    private var rootView : View? = null
    private var photosList = ArrayList<PhotoListItem>()
    private var photolistAdapter : PhotoListAdapter? = null
    private lateinit var photolistViewModel: PhotolistViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_photolist, container, false)
        photosList.clear()
        var item1 = PhotoListItem("https://mumbaimirror.indiatimes.com/thumb/msid-59719574,width-320,height-385,resizemode-4.cms", "Cool photo")
        var item2 = PhotoListItem("http://mumbaimirror.indiatimes.com/thumb/msid-59722654,width-320,height-385,resizemode-4.cms", "Bad photo")
        var item3 = PhotoListItem("http://mumbaimirror.indiatimes.com/thumb/msid-59722654,width-320,height-385,resizemode-4.cms", "Good photo")

        photosList.add(item1)
        photosList.add(item2)
        photosList.add(item3)

        photolistAdapter = PhotoListAdapter(this.context!!, photosList)

        photolistViewModel = ViewModelProviders.of(activity as FragmentActivity).get(PhotolistViewModel::class.java)
        observeViewModel(photolistViewModel)

        return rootView
    }

    override fun onStart() {
        super.onStart()
        photolist.adapter = photolistAdapter
        setListOnClickListener()
        (activity as AppCompatActivity).supportActionBar!!.title = "Photos"
    }

    private fun setListOnClickListener() {

        photolist?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val selectedItem = parent.getItemAtPosition(position) as PhotoListItem

            var photoFragment = PhotoFragment() as Fragment

            val fragmentManager = activity!!.supportFragmentManager
            val bundle = Bundle()
            bundle.putString("title", selectedItem.title)
            photoFragment.arguments = bundle
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.fragment, photoFragment, "PhotoFragment")
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    private fun observeViewModel(viewModel : PhotolistViewModel){
        viewModel.photolistResult.observe(this,  object : Observer<BaseResult> {
            override fun onChanged(@Nullable baseResult: BaseResult?) {
//                dismissProgressDialog()
                if (baseResult!!.isSuccess()){

                } else {
//                    activity.errorHandler(baseResult.resultDesc.toString(), "Filter failed!")
                }
            }
        })
    }
}