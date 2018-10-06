package com.arttttt.smokekerneltweaks.utils

import com.arttttt.smokekerneltweaks.base.SingletonHolder
import java.io.*



class SU private constructor(){
    companion object : SingletonHolder<SU>(::SU)

    private var mRootAccess: Boolean = false
    private lateinit var mProcess: Process
    private lateinit var mWriter: BufferedWriter
    private lateinit var mReader: BufferedReader

    fun initSuProcess() {
        try {
            mProcess = Runtime.getRuntime().exec("su")
            mWriter = BufferedWriter(OutputStreamWriter(mProcess.outputStream))
            mReader = BufferedReader(InputStreamReader(mProcess.inputStream))

            mRootAccess = true
        }
        catch (e: IOException) {
            mRootAccess = false
            throw IOException(e.cause)
        }
    }

    fun getRootAccess() {
        runCommand("echo /testRoot/")
    }

    fun runCommand(command: String) {
        try {
            if (mRootAccess) {

                val callback = "/shellCallback/"

                with(mWriter) {
                    write( "$command\n")
                    write("echo $callback\n")
                    flush()
                }

                while (true) {
                    val line = mReader.readLine() ?: break

                    if (line == callback)
                        break;
                }
            }
        }
        catch (e: IOException) {
            mRootAccess = false
            throw IOException(e.cause)
        }
    }
}