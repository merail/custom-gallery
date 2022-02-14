package me.rail.customgallery.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import me.rail.customgallery.R
import me.rail.customgallery.databinding.ActivityMainBinding
import me.rail.customgallery.main.permission.GoingToSettingsSnackbar
import me.rail.customgallery.main.permission.RuntimePermissionRequester
import me.rail.customgallery.media.MediaHandler
import me.rail.customgallery.screens.main.MainFragment
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigator: Navigator

    private lateinit var runtimePermissionRequester: RuntimePermissionRequester
    private var permissions = Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE }
    private lateinit var notGrantedPermissions: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setOnGetPermissionsClickListener()

        runtimePermissionRequester = RuntimePermissionRequester(this)

        if (!runtimePermissionRequester.checkSelfPermissions(permissions)) {
            runtimePermissionRequester.requestPermissions()
        }
    }

    override fun onStart() {
        super.onStart()

        checkRuntimePermissions()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        notGrantedPermissions = runtimePermissionRequester.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )

        setGetRuntimePermissionsVisibility(notGrantedPermissions.isEmpty())
        if (notGrantedPermissions.isEmpty()) {
            showMedia()
        }
    }

    private fun setOnGetPermissionsClickListener() {
        binding.requestPermission.setOnClickListener { view: View ->
            checkPermissionsForRationale()
            checkDeniedPermissions(view)
        }
    }

    private fun checkRuntimePermissions() {
        val isAllRuntimePermissionsGranted =
            runtimePermissionRequester.checkSelfPermissions(permissions)
        setGetRuntimePermissionsVisibility(isAllRuntimePermissionsGranted)
        if (isAllRuntimePermissionsGranted) {
            showMedia()
        }
    }

    private fun setGetRuntimePermissionsVisibility(hide: Boolean) {
        if (hide) {
            binding.permissionErrorContainer.visibility = View.GONE
        } else {
            binding.permissionErrorContainer.visibility = View.VISIBLE
        }
    }

    private fun checkPermissionsForRationale() {
        val permissionsForRationale =
            runtimePermissionRequester.getPermissionsForRationale(notGrantedPermissions)
        if (permissionsForRationale.isNotEmpty()) {
            runtimePermissionRequester.setPermissionsForRequest(permissionsForRationale)
            runtimePermissionRequester.requestPermissions()
        }
    }

    private fun checkDeniedPermissions(view: View) {
        val deniedPermissions =
            runtimePermissionRequester.getDeniedPermissions(notGrantedPermissions)
        if (deniedPermissions.isNotEmpty()) {
            val goingToSettingsSnackbar = GoingToSettingsSnackbar(this, view)
            goingToSettingsSnackbar.showSnackbar(
                "You must grant permissions in Settings!",
                "Settings"
            )
        }
    }

    private fun showMedia() {
        val mediaHandler = MediaHandler()
        mediaHandler.findMedia(applicationContext)

        navigator.replaceFragment(R.id.container, MainFragment())
    }
}