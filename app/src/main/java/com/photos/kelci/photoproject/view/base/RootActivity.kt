package com.kelci.familynote.view.base

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.LinearLayout


open class RootActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

    fun dismissProgressDialog() {
        try {
            if (progressDialog != null || progressDialog!!.isShowing) {
                progressDialog?.dismiss()
            }
        } catch (e: Exception) {
            return
        }

    }

    fun showAlertBox(errorMessage: String, title : String) {
        showAlertBox(errorMessage, title, 0)
    }

    fun showAlertBox(alertMessage: String?, title: String?, alignment: Int, onDismissListener: DialogInterface.OnDismissListener?) {
        var alertMessage = alertMessage
        try {
            if (alertMessage == null) alertMessage = ""

            val builder = AlertDialog.Builder(this)
            if (title == null) {
                //
            } else {
                val titleText = TextView(this)
                titleText.text = "\n" + title
                titleText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
                titleText.setTypeface(null, Typeface.BOLD)
                titleText.gravity = Gravity.CENTER
                builder.setCustomTitle(titleText)
            }

            builder.setMessage(alertMessage)
            builder.setPositiveButton("OK", null)

            val dialog = builder.create()
            dialog.window.setLayout(1000,800)
            dialog.show()
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val positiveButtonLL = positiveButton.layoutParams as LinearLayout.LayoutParams
            positiveButtonLL.gravity = Gravity.CENTER
            positiveButton.layoutParams = positiveButtonLL
            if (onDismissListener != null) {
                dialog.setOnDismissListener(onDismissListener)
            }
        } catch (e: Exception) {
        }

    }

    fun showAlertBox(alertMessage: String, title: String, alignment: Int) {
        showAlertBox(alertMessage, title, alignment, null)
    }

    fun showProgressDialog(message: String?) {
        var message = message
        if (message == null) message = ""

        if (progressDialog == null) {

            try {
                progressDialog = ProgressDialog.show(this, "", message, false)
                progressDialog?.show()
            } catch (e: Exception) {
            }

        }
    }

    fun showProgressDialog(message: String?, progressDialog: ProgressDialog?) {
        var message = message
        var progressDialog = progressDialog
        if (message == null) message = ""

        if (progressDialog == null) {

            try {
                progressDialog = ProgressDialog.show(this, "", message, false)
                progressDialog?.show()
            } catch (e: Exception) {
            }

        }
    }

    fun errorHandler(message: String, title: String) {
        dismissProgressDialog()
        showAlertBox(message, title)
    }

    fun showNetworkError() {
        errorHandler("Network not available.", "Network error!")
    }
}