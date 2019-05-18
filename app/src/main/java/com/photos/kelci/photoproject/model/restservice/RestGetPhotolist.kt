package com.photos.kelci.photoproject.model.restservice

import com.photos.kelci.photoproject.PhotoApplication
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.model.datastructure.BaseResult
import com.photos.kelci.photoproject.utilities.CommonCodes
import com.photos.kelci.photoproject.utilities.ServerResponseChecker
import org.json.JSONObject
import restclient.RestResult
import restclient.VolleyService

class RestGetPhotolist : VolleyService() {

    override fun parseResult(result: JSONObject?): RestResult<BaseResult> {
        val errorCode = ServerResponseChecker.onCheck(result.toString())
        if (errorCode != CommonCodes.NO_ERROR) {
            return RestResult(CommonCodes.NETWORK_ERROR, errorCode)
        }
        val baseResult = fromJson<BaseResult>(result.toString(), BaseResult::class.java)
        return RestResult(baseResult)
    }

    override fun getUrl(): String {

        val photolistString = PhotoApplication.photoApplication!!.getString(R.string.photolist)
        val serverURL = PhotoApplication.photoApplication!!.getString(R.string.server_url)

        return String.format(photolistString, serverURL)
    }
}