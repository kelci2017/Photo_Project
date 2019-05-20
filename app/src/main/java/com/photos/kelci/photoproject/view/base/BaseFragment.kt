package com.kelci.familynote.view.base

import android.support.v4.app.Fragment

open class BaseFragment : Fragment() {

    val name = BaseFragment::class.java.name
    fun getMainActivity(): RootActivity? {
        try {
            val activity = activity ?: return null
            return activity as RootActivity
        } catch (e: Exception) {
            return null
        }

    }

    fun showAlertBox(message : String?, title : String) {
        var message = message
        val mainActivity = getMainActivity() ?: return
        if (message == null) {
            return
        }
        if (message == null) message = ""
        dismissProgressDialog()
        mainActivity.showAlertBox(message, title)
    }

    fun showProgressDialog(message : String)
    {
        var mainActivity = getMainActivity ()

        mainActivity?.let { mainActivity.showProgressDialog(message) }
    }

    fun dismissProgressDialog() {
        val mainActivity = getMainActivity()
        mainActivity?.dismissProgressDialog()
    }
}