package com.oguzhanaslann.benchmark

import android.util.Log
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
/**
 * Benchmark, which will execute on an Android device.
 *
 * The body of [BenchmarkRule.measureRepeated] is measured in a loop, and Studio will
 * output the result. Modify your code to see how it affects performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun log() {
        val listToSort = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).shuffled()
        benchmarkRule.measureRepeated {

             listToSort = runWithTimingDisabled { unsorted.copyOf() }
            listToSort.sorted()
            Log.d("LogBenchmark", "the cost of writing this log method will be measured")
        }

        assertEquals(4, 2 + 2)
    }
}
