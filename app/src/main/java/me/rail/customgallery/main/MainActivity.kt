package me.rail.customgallery.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import me.rail.customgallery.R
import me.rail.customgallery.databinding.ActivityMainBinding
import me.rail.customgallery.media.MediaHandler
import me.rail.customgallery.screens.main.MainFragment
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        checkReadExternalStoragePermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        handleRequestCode(requestCode, grantResults)
    }

    private fun checkReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (isPermissionErrorVisible()) {
                hidePermissionError()
                showMedia()
            }
        } else {
            ActivityCompat.requestPermissions(
                this, Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE },
                REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION
            )
        }
    }

    private fun handleRequestCode(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMedia()
                } else {
                    handleRequestReadExternalStoragePermissionDenial()
                }
                return
            }
        }
    }

    private fun handleRequestReadExternalStoragePermissionDenial() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            showPermissionError()
        } else {
            showGoingToSettingsSnackbar()
        }
    }

    private fun showMedia() {
        val mediaHandler = MediaHandler()
        mediaHandler.findMedia(applicationContext)

        navigator.replaceFragment(R.id.container, MainFragment())
    }

    private fun showPermissionError() {
        binding.permissionErrorContainer.visibility = View.VISIBLE
        binding.requestPermission.setOnClickListener {
            checkReadExternalStoragePermission()
        }
    }

    private fun showGoingToSettingsSnackbar() {
        val goingToSettingsSnackbar = Snackbar.make(
            binding.root,
            resources.getString(R.string.instruction_for_read_external_storage_permission),
            Snackbar.LENGTH_LONG
        )
        goingToSettingsSnackbar.setAction(
            resources.getString(R.string.settings)
        ) {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri =
                Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        goingToSettingsSnackbar.show()
    }

    private fun hidePermissionError() {
        binding.permissionErrorContainer.visibility = View.GONE
    }

    private fun isPermissionErrorVisible() = binding.permissionErrorContainer.isVisible

    companion object {
        private const val REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 1
    }
}