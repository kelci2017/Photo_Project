package com.photos.kelci.photoproject.view.photo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kelci.familynote.view.base.BaseFragment
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.model.datastructure.BaseResult
import com.photos.kelci.photoproject.viewmodel.PhotoDetailViewModel
import com.photos.kelci.photoproject.viewmodel.PhotolistViewModel

class PhotoFragment : BaseFragment(){

    private var rootView : View? = null
    var title : String? = ""
    private lateinit var photoDetailViewModel: PhotoDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_photo, container, false)

        photoDetailViewModel = ViewModelProviders.of(activity as FragmentActivity).get(PhotoDetailViewModel::class.java)
        observeViewModel(photoDetailViewModel)

        return rootView
    }

    override fun setArguments(args: Bundle?) {
        this.title = args?.getString("title")

    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar!!.title = this.title
    }

    private fun observeViewModel(viewModel : PhotoDetailViewModel){
        viewModel.photoDetailResult.observe(this,  object : Observer<BaseResult> {
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