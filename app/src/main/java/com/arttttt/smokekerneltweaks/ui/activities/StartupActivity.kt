package com.arttttt.smokekerneltweaks.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arttttt.smokekerneltweaks.R
import com.arttttt.smokekerneltweaks.utils.su.SU
import kotlinx.android.synthetic.main.activity_startup.*
import java.lang.ref.WeakReference

class StartupActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        if (savedInstanceState == null) {
            StartupAsyncLoader(this) { rooted ->
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

    @SuppressLint("StaticFieldLeak")
    inner class StartupAsyncLoader(activity: AppCompatActivity, private val mCallback: (Boolean) -> Unit):
            AsyncTask<Void, Void, Void>() {

        private var mRooted = false

        private val activityReference = WeakReference(activity)

        override fun doInBackground(vararg params: Void?): Void? {
            val su = SU.getInstance()

            su.initSuProcess()
            mRooted = su.getRootAccess()

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return

            mCallback(mRooted)
        }
    }
}