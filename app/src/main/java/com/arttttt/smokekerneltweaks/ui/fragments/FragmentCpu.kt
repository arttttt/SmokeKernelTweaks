package com.arttttt.smokekerneltweaks.ui.fragments

import android.arch.lifecycle.ViewModelProviders
import com.arttttt.smokekerneltweaks.BR
import com.arttttt.smokekerneltweaks.R
import com.arttttt.smokekerneltweaks.databinding.FragmentCpuBinding
import com.arttttt.smokekerneltweaks.ui.base.FragmentBase
import com.arttttt.smokekerneltweaks.viewmodels.ViewModelCpu

class FragmentCpu: FragmentBase<ViewModelCpu, FragmentCpuBinding>() {

    companion object {
        private var INSTANCE: FragmentCpu? = null

        fun getInstance(): FragmentCpu {
            if (INSTANCE == null)
                INSTANCE = FragmentCpu()

            return INSTANCE as FragmentCpu
        }
    }

    override val mViewModel by lazy { ViewModelProviders.of(this).get(ViewModelCpu::class.java) }

    override fun getLayoutRes() = R.layout.fragment_cpu

    override fun getBindingVariable() = BR.cpuViewModel
}