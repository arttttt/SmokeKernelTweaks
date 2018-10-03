package com.arttttt.smokekerneltweaks.data

import android.os.Build

class DeviceData {
    val deviceName: String
        get() = Build.MODEL
    val codename: String
        get() = Build.DEVICE
}