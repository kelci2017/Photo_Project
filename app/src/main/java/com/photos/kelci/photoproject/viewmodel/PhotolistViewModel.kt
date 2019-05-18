package com.photos.kelci.photoproject.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.photos.kelci.photoproject.model.datastructure.BaseResult
import com.photos.kelci.photoproject.model.restservice.ServiceUtil
import com.photos.kelci.photoproject.utilities.CommonCodes
import restclient.RestHandler
import restclient.RestResult
import restclient.RestTag

class PhotolistViewModel : ViewModel() {

    var photolistResult = MutableLiveData<BaseResult>()

    fun getPhotolist() {
        var restHandler : RestHandler<BaseResult>? = null

        restHandler as RestHandler<Any>?

        var restTag = RestTag()
        restTag.tag = "Photolist"

        ServiceUtil.getPhotolist(restTag,null,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                val baseResult : BaseResult? = result?.resultObject as? BaseResult

                if (baseResult != null) {
                    photolistResult.value = baseResult
                } else {
                    val errorBaseResult = BaseResult(CommonCodes.NETWORK_ERROR, CommonCodes.NETWORK_ERROR_DESC)
                    photolistResult.value = errorBaseResult
                }
            }
        }, false)
    }
}