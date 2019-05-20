package com.photos.kelci.photoproject.utilities

import android.util.Log

class ServerResponseChecker {

    companion object {
        fun onCheck(response: String?): String {

            //Check if it is a connection error
            if (null == response || response == CommonCodes.NO_CONNECTION) {
                Log.i("ServerResponseChecker", "Error Code is: " + CommonCodes.NO_CONNECTION)
                return CommonCodes.NO_CONNECTION
            } else if (response == CommonCodes.HTTP_FAIL) {
                Log.i("ServerResponseChecker", "Error Code is: " + CommonCodes.HTTP_FAIL)

                return CommonCodes.HTTP_FAIL
            } else if (response == CommonCodes.RESPONSE_EMPTY) {
                Log.i("ServerResponseChecker", "Error Code is: " + CommonCodes.RESPONSE_EMPTY)


                return CommonCodes.RESPONSE_EMPTY
            }//Check if it is a Empty return
            //Check if it is a Http Error

            return CommonCodes.NO_ERROR
        }
    }

}