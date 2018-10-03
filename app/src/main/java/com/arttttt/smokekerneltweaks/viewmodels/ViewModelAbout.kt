package com.arttttt.smokekerneltweaks.viewmodels

import android.Manifest
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import android.os.Build
import com.arttttt.smokekerneltweaks.R
import com.arttttt.smokekerneltweaks.data.DeviceData
import com.arttttt.smokekerneltweaks.data.RomData
import com.arttttt.smokekerneltweaks.utils.PermissionsManager

class ViewModelAbout(application: Application) : AndroidViewModel(application) {

    val androidVersion: String
    val securityPatch: String
    val buildDate: String

    val deviceName: String
    val deviceCodename: String
    val deviceSerial = ObservableField<String>()

    init {
        updateData()

        val romData = RomData()
        androidVersion = romData.androidVersion
        securityPatch = romData.securityPatch
        buildDate = romData.buildDate

        val deviceData = DeviceData()
        deviceName = deviceData.deviceName
        deviceCodename = deviceData.codename
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

        deviceSerial.set(serial)
    }

}