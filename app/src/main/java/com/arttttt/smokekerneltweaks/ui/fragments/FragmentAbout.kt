package com.arttttt.smokekerneltweaks.ui.fragments

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Bundle
import com.arttttt.smokekerneltweaks.BR
import com.arttttt.smokekerneltweaks.R
import com.arttttt.smokekerneltweaks.databinding.FragmentAboutBinding
import com.arttttt.smokekerneltweaks.ui.base.FragmentBase
import com.arttttt.smokekerneltweaks.utils.PermissionsManager
import com.arttttt.smokekerneltweaks.viewmodels.ViewModelAbout

class FragmentAbout: FragmentBase<ViewModelAbout, FragmentAboutBinding>() {

    companion object {
        private var INSTANCE: FragmentAbout? = null

        fun getInstance(): FragmentAbout {
            if (INSTANCE == null)
                INSTANCE = FragmentAbout()

            return INSTANCE as FragmentAbout
        }
    }

    override val mViewModel by lazy { ViewModelProviders.of(this).get(ViewModelAbout::class.java) }

    override fun getLayoutRes() = R.layout.fragment_about
    override fun getBindingVariable() = BR.aboutViewModel

    private val PERMISSION_REQUEST_CODE = 11

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissions()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != PERMISSION_REQUEST_CODE)
            return

        if (permissions[0] == Manifest.permission.READ_PHONE_STATE &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED)
            mViewModel.permissionGranted()
    }

    private fun checkPermissions() {
        val granted = PermissionsManager.getInstance().checkPermission(context!!, Manifest.permission.READ_PHONE_STATE)

        if (!granted)
            PermissionsManager.getInstance().requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), PERMISSION_REQUEST_CODE, null)
    }
}