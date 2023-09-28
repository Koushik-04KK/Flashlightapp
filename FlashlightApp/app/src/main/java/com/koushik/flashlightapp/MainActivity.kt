package com.koushik.flashlightapp

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity

class FlashlightActivity : AppCompatActivity() {
    private lateinit var toggleButton: ToggleButton
    private lateinit var cameraManager: CameraManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.toggleButton = findViewById(R.id.flashlightToggle)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            toggleButton.isEnabled = false
            return
        }

        toggleButton.isChecked = false

        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                turnOnFlashlight()
            } else {
                turnOffFlashlight()
            }
        }
    }
    private fun turnOnFlashlight() {
        try {
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, true)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
    private fun turnOffFlashlight() {
        try {
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, false)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
    override fun onPause() {
        super.onPause()
        turnOffFlashlight()
    }
}