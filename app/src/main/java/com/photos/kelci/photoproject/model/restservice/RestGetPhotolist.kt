package com.photos.kelci.photoproject.model.restservice

import com.photos.kelci.photoproject.PhotoApplication
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.model.datastructure.BaseResult
import com.photos.kelci.photoproject.model.datastructure.ImageListItem
import com.photos.kelci.photoproject.utilities.CommonCodes
import com.photos.kelci.photoproject.utilities.ServerResponseChecker
import com.photos.kelci.photoproject.view.photolist.PhotoListItem
import org.json.JSONObject
import restclient.RestResult
import restclient.VolleyService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RestGetPhotolist : VolleyService() {

    override fun parseResult(result: JSONObject?): RestResult<ArrayList<PhotoListItem>> {
        val errorCode = ServerResponseChecker.onCheck(result.toString())

        if (errorCode != CommonCodes.NO_ERROR) {
            return RestResult(ArrayList<PhotoListItem>())
        }
        val baseResult = fromJson<BaseResult>(result.toString(), BaseResult::class.java)
        //check type before cast!!!!
        val gson = Gson()
        val type = object : TypeToken<ArrayList<ImageListItem>>() {}.type
        val jsonText = gson.toJson(baseResult.resultDesc)
        val imageArrayList = gson.fromJson(jsonText, type) as ArrayList<ImageListItem>

        val photolist = convertDataStructure(imageArrayList)
        return RestResult(photolist)
    }

    override fun getUrl(): String {

        val photolistString = PhotoApplication.photoApplication!!.getString(R.string.photolist)
        val serverURL = PhotoApplication.photoApplication!!.getString(R.string.server_url)

        return String.format(photolistString, serverURL)
    }

    private fun convertDataStructure(imageList : ArrayList<ImageListItem> ) : ArrayList<PhotoListItem> {
        var photolist = ArrayList<PhotoListItem>()

        for (imageItem in imageList) {
            val photoItem = PhotoListItem(imageItem.image_name, imageItem.image_title)
            photolist.add(photoItem)
        }
        return photolist
    }
}