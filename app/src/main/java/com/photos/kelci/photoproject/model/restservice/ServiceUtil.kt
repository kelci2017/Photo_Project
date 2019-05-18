package com.photos.kelci.photoproject.model.restservice

import restclient.RestHandler
import restclient.RestParms
import restclient.RestTag

class ServiceUtil {

    companion object {

        @Synchronized
        fun getPhotolist(restTag: RestTag?, restParms: RestParms?, restHandler: RestHandler<Any>?, useCache: Boolean) {
            RestGetPhotolist().call(restTag, restParms, restHandler, useCache)
        }

        @Synchronized
        fun getPhotoDetail(restTag: RestTag?, restParms: RestParms?, restHandler: RestHandler<Any>?, useCache: Boolean) {
            RestGetPhotoDetail().call(restTag, restParms, restHandler, useCache)
        }
    }
}