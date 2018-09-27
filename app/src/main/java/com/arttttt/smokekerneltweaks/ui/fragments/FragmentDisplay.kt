package com.arttttt.smokekerneltweaks.ui.fragments

import android.arch.lifecycle.ViewModelProviders
import com.arttttt.smokekerneltweaks.BR
import com.arttttt.smokekerneltweaks.R
import com.arttttt.smokekerneltweaks.databinding.FragmentDisplayBinding
import com.arttttt.smokekerneltweaks.ui.base.FragmentBase
import com.arttttt.smokekerneltweaks.viewmodels.ViewModelDisplay

class FragmentDisplay: FragmentBase<ViewModelDisplay, FragmentDisplayBinding>() {

    companion object {
        private var INSTANCE: FragmentDisplay? = null

        fun getInstance(): FragmentDisplay {
            if (INSTANCE == null)
                INSTANCE = FragmentDisplay()

            return INSTANCE as FragmentDisplay
        }
    }

    override val mViewModel by lazy { ViewModelProviders.of(this).get(ViewModelDisplay::class.java) }

    override fun getLayoutRes() = R.layout.fragment_display

    override fun getBindingVariable() = BR.cpuViewModel
}