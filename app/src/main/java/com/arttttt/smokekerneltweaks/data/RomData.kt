package com.arttttt.smokekerneltweaks.data

import android.os.Build
import java.text.DateFormat

class RomData {
    val androidVersion: String
        get() = Build.VERSION.RELEASE
    val securityPatch: String
        get() = Build.VERSION.SECURITY_PATCH
    val buildDate: String
        get() = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(Build.TIME)
}