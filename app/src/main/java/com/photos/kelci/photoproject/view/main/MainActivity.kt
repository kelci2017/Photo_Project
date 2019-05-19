package com.photos.kelci.photoproject.view.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.photos.kelci.photoproject.R
import com.photos.kelci.photoproject.view.photolist.PhotoListFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import com.kelci.familynote.view.base.RootActivity


class MainActivity : RootActivity() {

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
}
