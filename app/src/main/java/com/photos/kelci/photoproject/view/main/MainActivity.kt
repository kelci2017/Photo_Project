package com.photos.kelci.photoproject.view.main

import android.os.Bundle
import android.support.v4.app.Fragment
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.view.photolist.PhotoListFragment
import kotlinx.android.synthetic.main.activity_main.*
import com.kelci.familynote.view.base.RootActivity




class MainActivity : RootActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val fragment = supportFragmentManager.findFragmentByTag("PhotolistFragment")
        val photoListFragment : PhotoListFragment
        if(fragment == null){
            photoListFragment = PhotoListFragment()
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.add(R.id.fragment, photoListFragment, "PhotolistFragment")

            fragmentTransaction.commit()
        }else{
            photoListFragment = fragment as PhotoListFragment
        }

//        var photoListFragment = PhotoListFragment() as Fragment

//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//
//        fragmentTransaction.add(R.id.fragment, photoListFragment, "PhotolistFragment")
//
//        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val f = supportFragmentManager.findFragmentByTag("PhotolistFragment")
        if (f is PhotoListFragment) {
            supportActionBar!!.title = "Photos"
        }
    }
}
