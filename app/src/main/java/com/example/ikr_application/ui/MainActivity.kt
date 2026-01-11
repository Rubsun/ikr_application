package com.example.ikr_application.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.features.screens.api.ScreensConstants
import com.example.ikr_application.R
import com.example.injector.get
import com.example.libs.arch.ScreenFragmentRouter
import com.example.libs.arch.navigationDestination
import com.example.libs.arch.payload
import org.koin.core.qualifier.named
import com.example.libs.arch.R as archR

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(archR.id.fragment_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            get<ScreenFragmentRouter>(named(ScreensConstants.SCREENS_ID))
                .launch(this, false, null)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val name = navigationDestination(intent)
        if (name == null) {
            return
        }

        val payload = intent.payload()
        get<ScreenFragmentRouter>(named(name))
            .launch(this, true, payload)
    }
}