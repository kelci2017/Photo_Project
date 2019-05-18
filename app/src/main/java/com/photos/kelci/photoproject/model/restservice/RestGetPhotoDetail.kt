package com.photos.kelci.photoproject.model.restservice

import com.photos.kelci.photoproject.PhotoApplication
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.model.datastructure.BaseResult
import com.photos.kelci.photoproject.utilities.CommonCodes
import com.photos.kelci.photoproject.utilities.ServerResponseChecker
import org.json.JSONObject
import restclient.RestResult
import restclient.VolleyService

class RestGetPhotoDetail: VolleyService() {
    var photoId : String = ""

    override fun parseResult(result: JSONObject?): RestResult<BaseResult> {
        val errorCode = ServerResponseChecker.onCheck(result.toString())
        if (errorCode != CommonCodes.NO_ERROR) {
            return RestResult(CommonCodes.NETWORK_ERROR, errorCode)
        }
        val baseResult = fromJson<BaseResult>(result.toString(), BaseResult::class.java)
        return RestResult(baseResult)
    }

    override fun getUrl(): String {

        val photoString = PhotoApplication.photoApplication!!.getString(R.string.photodetail)
        val serverURL = PhotoApplication.photoApplication!!.getString(R.string.server_url)

        return String.format(photoString, serverURL)
    }

    override fun initialize(): RestResult<Any> {
        photoId = getParameter("photoId") as String

        return RestResult()
    }
}