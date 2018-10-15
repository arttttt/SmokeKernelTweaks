package com.arttttt.smokekerneltweaks.base

open class NewInstanceCreator<out T, in A>(private val creator: (A) -> T) {
    fun newInstance(arg: A): T = creator(arg)
}