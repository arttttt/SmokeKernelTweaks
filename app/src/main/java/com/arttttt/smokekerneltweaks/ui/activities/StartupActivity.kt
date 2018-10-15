package com.arttttt.smokekerneltweaks.ui.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.arttttt.smokekerneltweaks.R
import com.arttttt.smokekerneltweaks.utils.su.SU
import kotlinx.android.synthetic.main.activity_startup.*

class StartupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        if (savedInstanceState == null) {
            StartupAsyncLoader {rooted ->
                statusTextView.setText(if (rooted)
                    R.string.root_access_success
                else
                    R.string.root_access_fail)

                if (rooted) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }.execute()
        }
    }

    inner class StartupAsyncLoader(private val mCallback: (Boolean) -> Unit): AsyncTask<Void, Int, Void>() {

        private var mRooted = false

        override fun doInBackground(vararg params: Void?): Void? {
            val su = SU.getInstance()

            su.initSuProcess()
            mRooted = su.getRootAccess()

            Log.d("TEST!", mRooted.toString())

            publishProgress(0)

            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)

            when(values[0]) {
                0 -> mCallback(mRooted)
            }
        }
    }
}