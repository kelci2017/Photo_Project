package com.photos.kelci.photoproject.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.photos.kelci.photoproject.PhotoApplication
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.model.datastructure.BaseResult
import com.photos.kelci.photoproject.model.datastructure.ImageDetail
import com.photos.kelci.photoproject.model.restservice.ServiceUtil
import com.photos.kelci.photoproject.view.photo.PhotoDetail
import restclient.RestHandler
import restclient.RestParms
import restclient.RestResult
import restclient.RestTag

class PhotoDetailViewModel : ViewModel() {

    var photoDetailResult = MutableLiveData<PhotoDetail>()

    fun getPhotoDetail(photoName : String?) {
        var restHandler : RestHandler<BaseResult>? = null

        restHandler as RestHandler<Any>?

        var restTag = RestTag()
        restTag.tag = "PhotoDetail"
        var restParams : RestParms = RestParms()

        restParams.setParams(PhotoApplication.photoApplication!!.getString(R.string.photoName), photoName)


        ServiceUtil.getPhotoDetail(restTag,restParams,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                val baseResult : PhotoDetail? = result?.resultObject as? PhotoDetail

                photoDetailResult.value = baseResult
            }
        }, false)
    }
}