package com.arttttt.smokekerneltweaks.models

import android.databinding.ObservableField
import android.os.Build

class DeviceData {
    val deviceName: String
        get() = Build.MODEL
    val codename: String
        get() = Build.DEVICE
    val deviceSerial = ObservableField<String>()
}