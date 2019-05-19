package com.photos.kelci.photoproject.view.main

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.util.LruCache
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.view.photolist.PhotoListFragment
import kotlinx.android.synthetic.main.activity_main.*
import com.kelci.familynote.view.base.RootActivity




class MainActivity : RootActivity() {

//    private lateinit var memoryCache: LruCache<String, Bitmap>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var photoListFragment = PhotoListFragment() as Fragment

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.fragment, photoListFragment, "PhotolistFragment")
//        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val f = supportFragmentManager.findFragmentByTag("PhotolistFragment")
        if (f is PhotoListFragment) {
            supportActionBar!!.title = "Photos"
        }
    }
}
