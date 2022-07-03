package com.oguzhanaslann.macro_benchmark

import androidx.benchmark.macro.*
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    private val packageName = "com.oguzhanaslann.performancepractise"

    private val iterationCount = 1

    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = packageName,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterationCount,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }

    // startup mode = warm
    @Test
    fun startupWarm() = benchmarkRule.measureRepeated(
        packageName = packageName,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterationCount,
        startupMode = StartupMode.WARM
    ) {
        pressHome()
        startActivityAndWait()
    }

    // startup mode = hot
    @Test
    fun startupHot() = benchmarkRule.measureRepeated(
        packageName = packageName,
        metrics = listOf(
            StartupTimingMetric(),
            FrameTimingMetric()
        ),
        iterations = iterationCount,
        startupMode = StartupMode.HOT
    ) {
        pressHome()
        startActivityAndWait()
    }

    // trace main text view click
    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun traceMainTextViewClick() = benchmarkRule.measureRepeated(
        packageName = packageName,
        metrics = listOf(
            StartupTimingMetric(),
            FrameTimingMetric(),
            TraceSectionMetric("Main Text Click")
        ),
        iterations = iterationCount,
        startupMode = StartupMode.COLD,
        setupBlock =  {
            pressHome()
            startActivityAndWait()
        }
    ) {
        val textView = device.findObject(By.res(packageName, "textView"))
        textView.click()
    }
}
