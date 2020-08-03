package com.example.crashobfuscationtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.linphone.core.Factory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnForceCrash = findViewById<Button>(R.id.btn_force_crash)
        btnForceCrash.setOnClickListener { forceCrash() }
    }

    private fun forceCrash() {
        Factory.instance().setDebugMode(true, "Linphone")
        val core = Factory.instance().createCore(null, null, this.getApplicationContext())
        core.start()
        core.adaptiveRateAlgorithm = null
    }
}