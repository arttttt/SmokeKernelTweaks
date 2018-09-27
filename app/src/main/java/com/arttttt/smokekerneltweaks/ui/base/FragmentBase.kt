package com.arttttt.smokekerneltweaks.ui.base

import android.arch.lifecycle.AndroidViewModel
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class FragmentBase<T: AndroidViewModel, U: ViewDataBinding> : Fragment() {

    lateinit var mBinding: U
    abstract val mViewModel: T

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun getBindingVariable(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        mBinding.setVariable(getBindingVariable(), mViewModel)

        return mBinding.root
    }
}