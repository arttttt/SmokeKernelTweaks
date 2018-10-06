package com.arttttt.smokekerneltweaks.utils.su

import com.arttttt.smokekerneltweaks.base.SingletonHolder
import java.io.*
import java.lang.StringBuilder


class SU private constructor(){
    companion object : SingletonHolder<SU>(::SU)

    private var mRootAccess: Boolean = false
    private var mProcess: Process? = null
    private var mWriter: BufferedWriter? = null
    private var mReader: BufferedReader? = null

    fun initSuProcess() {
        try {
            if (mProcess == null) {
                val process = Runtime.getRuntime().exec("su")
                mWriter = BufferedWriter(OutputStreamWriter(process.outputStream))
                mReader = BufferedReader(InputStreamReader(process.inputStream))

                mProcess = process

                mRootAccess = true
            }
        }
        catch (e: IOException) {
            mRootAccess = false
            e.printStackTrace()
        }
    }

    fun getRootAccess() {
        runCommand("echo /testRoot/")
    }

    fun runCommand(command: String): String? {
        try {
            if (mRootAccess) {

                val callback = "/shellCallback/"

                mWriter?.apply {
                    write( "$command\n")
                    write("echo $callback\n")
                    flush()
                }

                val sb = StringBuilder()

                while (true) {
                    val line = mReader?.readLine()

                    if (line == callback)
                        break

                    sb.append("$line\n")
                }

                return sb.toString().trim()
            }
        }
        catch (e: IOException) {
            mRootAccess = false
            e.printStackTrace()
        }

        return null
    }

    fun close() {
        try {
            mWriter?.write("exit\n");
            mWriter?.flush()
            mWriter?.close()

            mReader?.close()

            mProcess?.waitFor()
            mProcess?.destroy()

            mRootAccess = false
        }
        catch (e: InterruptedException) {
            e.printStackTrace()
        }
        catch (e:IOException) {
            e.printStackTrace()
        }
    }
}