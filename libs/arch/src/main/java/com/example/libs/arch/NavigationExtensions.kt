package com.example.libs.arch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri
import androidx.fragment.app.Fragment

private const val FRAGMENT_PAYLOAD = "extras.fragment_payload"

fun Fragment.navigateTo(name: String, payload: Bundle? = null) {
    val scheme = getString(R.string.deeplink_navigation_scheme)
    val host = getString(R.string.deeplink_navigation_host)
    val deeplink = "$scheme://$host/$name".toUri()
    val intent = Intent(Intent.ACTION_VIEW, deeplink).apply {
        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        payload
            ?.let { putExtra(FRAGMENT_PAYLOAD, it) }
    }

    startActivity(intent)
}

fun Activity.navigationDestination(intent: Intent): String? {
    val scheme = getString(R.string.deeplink_navigation_scheme)
    val host = getString(R.string.deeplink_navigation_host)

    return intent.data
        ?.takeIf { uri -> uri.scheme == scheme && uri.host == host }
        ?.pathSegments
        ?.firstOrNull()
}

fun Intent.payload(): Bundle? {
    return getBundleExtra(FRAGMENT_PAYLOAD)
}