package com.arttttt.smokekerneltweaks.viewmodels

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.os.Build
import com.arttttt.smokekerneltweaks.R
import com.arttttt.smokekerneltweaks.models.DeviceData
import com.arttttt.smokekerneltweaks.models.RomData
import com.arttttt.smokekerneltweaks.utils.PermissionsManager

class ViewModelAbout(application: Application) : AndroidViewModel(application) {
    val deviceData = DeviceData()
    val romData = RomData()

    init {
        updateData()
    }

    fun permissionGranted() {
        updateData()
    }

    private fun updateData() {
        val serial = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            if (PermissionsManager.getInstance().checkPermission(getApplication(), Manifest.permission.READ_PHONE_STATE)) {
                Build.getSerial()
            } else {
                getApplication<Application>().getString(R.string.permission_grant_failed)
            }
        else
            Build.SERIAL

        deviceData.deviceSerial.set(serial)
    }

}