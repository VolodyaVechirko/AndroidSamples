package com.vvechirko.camerax

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vvechirko.camerax.ui.AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.i("App", "Permission granted")
        } else {
            Log.i("App", "Permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen()
            }
        }

        requestCameraPermission()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.locations.collect { }
            }
        }
    }

    private fun requestCameraPermission() {
        val cameraPermission = android.Manifest.permission.CAMERA
        when {
            checkSelfPermission(this, cameraPermission) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("App", "Permission previously granted")
            }

            shouldShowRequestPermissionRationale(this, cameraPermission) -> {
                Log.i("App", "Show camera permissions dialog")
            }

            else -> permissionLauncher.launch(cameraPermission)
        }
    }
}