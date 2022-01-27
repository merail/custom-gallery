package me.rail.customgallery

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import me.rail.customgallery.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        ActivityCompat.requestPermissions(
            this,
            Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE },
            REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val mediaHandler = MediaHandler()
                    mediaHandler.findImages(applicationContext)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Permission denied to read your External storage",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 1
    }
}