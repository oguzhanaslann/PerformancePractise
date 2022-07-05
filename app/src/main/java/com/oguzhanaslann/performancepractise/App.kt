package com.oguzhanaslann.performancepractise

import android.app.Application
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.util.Log

class App : Application() {
    override fun onCreate() {
        super.onCreate()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            Log.d("TAG", "onCreate: ${usageStatsManager.appStandbyBucket}" )
        }

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
