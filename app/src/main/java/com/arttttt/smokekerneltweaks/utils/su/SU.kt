package com.arttttt.smokekerneltweaks.utils.su

import com.arttttt.smokekerneltweaks.base.SingletonHolder
import java.io.*
import java.lang.StringBuilder


class SU private constructor(){
    companion object : SingletonHolder<SU>(::SU)

    private var mDenied = false
    private var mFirstTry = true
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
            }
        }
        catch (e: IOException) {
            mDenied = true
            e.printStackTrace()
        }
    }

    fun getRootAccess(): Boolean = let {
        runCommand("echo /testRoot/")
        !mDenied
    }

    fun runCommand(command: String): String? {
        try {
            if (!mDenied || mFirstTry) {
                mFirstTry = false

                val callback = "/shellCallback/"

                mWriter?.apply {
                    write( "$command\n")
                    write("echo $callback\n")
                    flush()
                }

                val sb = StringBuilder()

                while (true) {
                    val line = mReader?.readLine()

                    if (line == null) {
                        mDenied = true
                        break
                    }

                    if (line == callback)
                        break

                    sb.append("$line\n")
                }

                return sb.toString().trim()
            }
        }
        catch (e: IOException) {
            mDenied = true
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

            mDenied = false
        }
        catch (e: InterruptedException) {
            e.printStackTrace()
        }
        catch (e:IOException) {
            e.printStackTrace()
        }
    }
}