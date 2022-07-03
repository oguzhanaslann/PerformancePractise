package com.oguzhanaslann.performancepractise

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.trace
import com.oguzhanaslann.performancepractise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textView.onClick {
            trace("Main Text Click") {
                text = text.toString() + text.last().toString()
            }
        }
    }
}

inline fun <reified T : View> T.onClick( crossinline l: T.() -> Unit) {
    setOnClickListener { l(it as T) }
}
