package com.itsjames.android_demo_lib

import android.util.Log

class HelloWorld {
    companion object {
        private const val TAG = "MyLogger"

        fun printLog() {
            Log.d(TAG, "james")
        }
    }
}