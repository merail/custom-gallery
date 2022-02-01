package me.rail.customgallery.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import me.rail.customgallery.R
import javax.inject.Inject

class Navigator @Inject constructor(activity: FragmentActivity) {
    private val fragmentManager = activity.supportFragmentManager

    fun replaceFragment(fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.container, fragment)

        transaction.commit()
    }
}