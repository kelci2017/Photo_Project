package com.photos.kelci.photoproject.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.photos.kelci.photoproject.model.datastructure.BaseResult
import com.photos.kelci.photoproject.model.restservice.ServiceUtil
import com.photos.kelci.photoproject.utilities.CommonCodes
import com.photos.kelci.photoproject.view.photolist.PhotoListItem
import restclient.RestHandler
import restclient.RestResult
import restclient.RestTag

class PhotolistViewModel : ViewModel() {

    var photolistResult = MutableLiveData<ArrayList<PhotoListItem>>()

    fun getPhotolist() {
        var restHandler : RestHandler<ArrayList<PhotoListItem>>? = null

        restHandler as RestHandler<Any>?

        var restTag = RestTag()
        restTag.tag = "Photolist"

        ServiceUtil.getPhotolist(restTag,null,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                val baseResult : ArrayList<PhotoListItem>? = result?.resultObject as? ArrayList<PhotoListItem>
                photolistResult.value = baseResult
            }
        }, false)
    }
}