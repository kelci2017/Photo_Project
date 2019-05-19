package com.photos.kelci.photoproject.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.photos.kelci.photoproject.model.datastructure.BaseResult
import com.photos.kelci.photoproject.model.datastructure.ImageDetail
import com.photos.kelci.photoproject.model.restservice.ServiceUtil
import com.photos.kelci.photoproject.utilities.CommonCodes
import restclient.RestHandler
import restclient.RestParms
import restclient.RestResult
import restclient.RestTag

class PhotoDetailViewModel : ViewModel() {

    var photoDetailResult = MutableLiveData<ImageDetail>()

    fun getPhotoDetail(photoName : String?) {
        var restHandler : RestHandler<BaseResult>? = null

        restHandler as RestHandler<Any>?

        var restTag = RestTag()
        restTag.tag = "PhotoDetail"
        var restParams : RestParms = RestParms()

        restParams.setParams("photoName", photoName)


        ServiceUtil.getPhotoDetail(restTag,restParams,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                val baseResult : ImageDetail? = result?.resultObject as? ImageDetail

                photoDetailResult.value = baseResult
            }
        }, false)
    }
}