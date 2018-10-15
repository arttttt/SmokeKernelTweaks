package com.arttttt.smokekerneltweaks.utils.su

import com.arttttt.smokekerneltweaks.base.NewInstanceCreator

class RootFile private constructor(file: String){
    companion object : NewInstanceCreator<RootFile, String>(::RootFile)

    private val mSu by lazy { SU.getInstance() }

    fun read(file: String): String? {
        return mSu.runCommand("cat $file")
    }

    fun write(file: String, data: String, append: Boolean) {
        val action = if (append)
            ">>"
        else
            ">"

        data.split("\\r?\\n").forEach {line ->
            mSu.runCommand("echo $line $action $file")
        }
    }

    fun remove(file: String) {
        val command = if (isDirectory(file))
            "\"rm -r $file\""
        else
            "rm $file"

        mSu.runCommand(command)
    }

    fun isDirectory(file: String): Boolean {
        return mSu.runCommand("[ -d '$file' ] && echo true") == "true"
    }
}