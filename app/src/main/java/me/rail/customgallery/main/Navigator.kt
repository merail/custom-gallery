package me.rail.customgallery.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import me.rail.customgallery.R
import javax.inject.Inject

class Navigator @Inject constructor(activity: FragmentActivity) {
    private val fragmentManager = activity.supportFragmentManager

    fun replaceFragment(container: Int, fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()

        if (container == R.id.fragmentContainer) {
            transaction.replace(container, fragment)
        } else {
            transaction
                .addToBackStack(fragment::class.java.name)
                .add(container, fragment)
        }

        transaction.commit()
    }
}