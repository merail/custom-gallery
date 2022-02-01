package me.rail.customgallery.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import me.rail.customgallery.R
import me.rail.customgallery.albumlist.AlbumListFragment
import me.rail.customgallery.databinding.ActivityMainBinding
import me.rail.customgallery.media.MediaHandler
import me.rail.customgallery.medialist.MediaListFragment
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupBottomNavigationView()

        checkReadExternalStoragePermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMedia()
                } else {
                    showToast("Permission denied to read your External storage")
                }
                return
            }
        }
    }

    private fun setupBottomNavigationView() {
        binding.navigation.selectedItemId = R.id.mediaPage
        binding.navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mediaPage -> {
                    navigator.replaceFragment(MediaListFragment())
                }
                R.id.albumsPage -> {
                    navigator.replaceFragment(AlbumListFragment())
                }
                else -> {
                }
            }

            return@setOnItemSelectedListener true
        }
        binding.navigation.setOnItemReselectedListener {

        }
    }

    private fun checkReadExternalStoragePermission() {
        ActivityCompat.requestPermissions(
            this,
            Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE },
            REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION
        )
    }

    private fun showMedia() {
        val mediaHandler = MediaHandler()
        mediaHandler.findImages(applicationContext)

        navigator.replaceFragment(MediaListFragment())
    }

    private fun showToast(text: String) {
        Toast.makeText(
            this@MainActivity,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 1
    }
}