package com.oguzhanaslann.performancepractise

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.PerformanceMetricsState
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.oguzhanaslann.performancepractise.databinding.ActivityJanksStatsBinding
import com.oguzhanaslann.performancepractise.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class JanksStatsActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityJanksStatsBinding.inflate(layoutInflater)
    }

    private lateinit var jankStats: JankStats

    private val jankFrameListener = JankStats.OnFrameListener { frameData ->
        // A real app could do something more interesting, like writing the info to local storage and later on report it.
        Log.v("JankFrame", frameData.toString())
        if (frameData.isJank) {
            Log.e("JankFrame", "Jank detected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val metricsStateHolder = PerformanceMetricsState.getForHierarchy(binding.root)

        // initialize JankStats for current window
        jankStats = JankStats.createAndTrack(
            window,
            Dispatchers.Default.asExecutor(),
            jankFrameListener,
        )

        // add activity name as state
        metricsStateHolder.state?.addState("Activity", javaClass.simpleName)


        binding.textView.apply {
            var click = 0
            setOnClickListener {
                metricsStateHolder.state?.addState("Click", "$click")
                runBlocking {
                    delay(1500) // simulate long running task to measure jank
                }
                text = "Clicked! $click"
                click++

                if (click > 10) {
                    metricsStateHolder.state?.removeState("Click")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        jankStats.isTrackingEnabled = true
    }

    override fun onPause() {
        super.onPause()
        jankStats.isTrackingEnabled = false
    }
}
