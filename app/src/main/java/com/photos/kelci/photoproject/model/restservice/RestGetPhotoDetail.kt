package com.photos.kelci.photoproject.model.restservice

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.photos.kelci.photoproject.PhotoApplication
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.model.datastructure.BaseResult
import com.photos.kelci.photoproject.model.datastructure.ImageDetail
import com.photos.kelci.photoproject.model.datastructure.ImageListItem
import com.photos.kelci.photoproject.utilities.CommonCodes
import com.photos.kelci.photoproject.utilities.ServerResponseChecker
import org.json.JSONObject
import restclient.RestResult
import restclient.VolleyService

class RestGetPhotoDetail: VolleyService() {
    var photoName : String = ""

    override fun parseResult(result: JSONObject?): RestResult<ImageDetail> {
        val errorCode = ServerResponseChecker.onCheck(result.toString())
        val nullImageDetail = ImageDetail(null, null, null, null, null)
        if (errorCode != CommonCodes.NO_ERROR) {
            return RestResult(nullImageDetail)
        }
        val baseResult = fromJson<BaseResult>(result.toString(), BaseResult::class.java)
        if (!baseResult.isSuccess()) return RestResult(nullImageDetail)
        val gson = Gson()
        val type = object : TypeToken<ImageDetail>() {}.type
        val jsonText = gson.toJson(baseResult.resultDesc)
        val imageDetail = gson.fromJson(jsonText, type) as ImageDetail

        return RestResult(imageDetail)
    }

    override fun getUrl(): String {

        val photoString = PhotoApplication.photoApplication!!.getString(R.string.photodetail)
        val serverURL = PhotoApplication.photoApplication!!.getString(R.string.server_url)

        return String.format(photoString, serverURL, photoName)
    }

    override fun initialize(): RestResult<Any> {
        photoName = getParameter(PhotoApplication.photoApplication!!.getString(R.string.photoName)) as String

        return RestResult()
    }
}