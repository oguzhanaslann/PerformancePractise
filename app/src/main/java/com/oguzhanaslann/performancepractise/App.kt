package com.oguzhanaslann.performancepractise

import android.app.Application
import android.os.Build
import android.os.StrictMode

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        StrictMode.setVmPolicy(
            StrictMode.VmPolicy
                .Builder()
                .detectActivityLeaks()
                .detectCleartextNetwork()
                .detectActivityLeaks()
                .detectLeakedSqlLiteObjects()
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        detectContentUriWithoutPermission()
                    }
                }
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        detectUntaggedSockets()
                    }
                }
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        detectContentUriWithoutPermission()
                    }
                }
                .build()
        )

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectNetwork()
                .detectDiskWrites()
                .detectResourceMismatches()
                .detectAll()
                .build()
        )
    }
}
