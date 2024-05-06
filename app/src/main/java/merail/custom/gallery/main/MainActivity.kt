package merail.custom.gallery.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import merail.custom.gallery.R
import merail.custom.gallery.databinding.ActivityMainBinding
import merail.custom.gallery.media.MediaHandler
import merail.custom.gallery.screens.main.MainFragment
import merail.tools.permissions.SettingsSnackbar
import merail.tools.permissions.runtime.RuntimePermissionRequester
import merail.tools.permissions.runtime.RuntimePermissionState
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigator: Navigator

    private lateinit var runtimePermissionRequester: RuntimePermissionRequester

    private var permissions = when (Build.VERSION.SDK_INT) {
        Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED,
        )
        Build.VERSION_CODES.TIRAMISU -> arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
        )
        else -> arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        runtimePermissionRequester = RuntimePermissionRequester(
            activity = this,
            requestedPermissions = permissions,
        )

        if (runtimePermissionRequester.areAllPermissionsGranted().not()) {
            runtimePermissionRequester.requestPermissions {
                if (runtimePermissionRequester.areAllPermissionsGranted()) {
                    showMedia()
                } else if (it.containsValue(RuntimePermissionState.PERMANENTLY_DENIED)) {
                    SettingsSnackbar(this, binding.root).showSnackbar(
                        text = "You must grant permissions in Settings!",
                        actionName = "Settings",
                    )
                }
            }
        }
    }

    private fun showMedia() {
        val mediaHandler = MediaHandler()
        mediaHandler.findMedia(applicationContext)

        navigator.replaceFragment(R.id.container, MainFragment())
    }
}