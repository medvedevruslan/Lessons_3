package com.example.suspend_coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    private val tag = "developer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            runBlocking {
                log("start")
                async { loadCat(1, 1000) }
                async { loadCat(2, 200) }

                log("finish")
            }
        }
    }

    private suspend fun loadCat(number: Int, delay: Long) {
        log("Sload $number")
        delay(delay)
        log("Sload cat#$number complete")
    }

    private fun loadCat1(number: Int, delay: Long) {
        log("load $number")
        sleep(delay)
        log("load cat#$number complete")
    }

    private fun log(message: String) = Log.d(tag, message)
}