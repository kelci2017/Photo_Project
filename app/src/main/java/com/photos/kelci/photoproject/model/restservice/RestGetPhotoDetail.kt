package com.photos.kelci.photoproject.model.restservice

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.photos.kelci.photoproject.PhotoApplication
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.model.datastructure.BaseResult
import com.photos.kelci.photoproject.model.datastructure.ImageDetail
import com.photos.kelci.photoproject.utilities.CommonCodes
import com.photos.kelci.photoproject.utilities.ServerResponseChecker
import com.photos.kelci.photoproject.view.photo.PhotoDetail
import org.json.JSONObject
import restclient.RestResult
import restclient.VolleyService

class RestGetPhotoDetail: VolleyService() {
    var photoName : String = ""

    override fun parseResult(result: JSONObject?): RestResult<PhotoDetail> {
        val errorCode = ServerResponseChecker.onCheck(result.toString())
        val nullImageDetail = PhotoDetail(null, null, null, null, null, null, null)
        if (errorCode != CommonCodes.NO_ERROR) {
            return RestResult(nullImageDetail)
        }
        val baseResult = fromJson<BaseResult>(result.toString(), BaseResult::class.java)
        if (!baseResult.isSuccess()) return RestResult(nullImageDetail)
        val gson = Gson()
        val type = object : TypeToken<ImageDetail>() {}.type
        val jsonText = gson.toJson(baseResult.resultDesc)
        val imageDetail = gson.fromJson(jsonText, type) as ImageDetail

        val photoDetail = convertDataStructure(imageDetail)

        return RestResult(photoDetail)
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

    private fun convertDataStructure(imageDetail : ImageDetail ) : PhotoDetail {
        val photoDetailString = PhotoApplication.photoApplication!!.getString(R.string.detailphoto)
        val serverURL = PhotoApplication.photoApplication!!.getString(R.string.server_url)

        val photoDetail = PhotoDetail(this.photoName, String.format(photoDetailString, serverURL, this.photoName), imageDetail.photographer, imageDetail.location, imageDetail.likes, imageDetail.filter, imageDetail.date)

        return photoDetail
    }
}