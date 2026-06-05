package com.example.kmpclient

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.res.Configuration

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ResourcesManager with context
        ResourcesManager.init(this)

        setContent {
            App()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Update resources when configuration changes (language or orientation)
        ResourcesManager.updateResources()
    }
}

