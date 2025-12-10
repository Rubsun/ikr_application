package com.example.ikr_application.navigation

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.ikr_application.R
import com.example.ikr_application.nfirex.ui.MyFragment
import com.example.ikr_application.dem_module.ui.SampleFragment

enum class Screens(
    @StringRes val title: Int,
    val type: Class<out Fragment>,
) {
    SAMPLE(R.string.title_sample, MyFragment::class.java),
    DEM_MODULE(R.string.title_dem_module, SampleFragment::class.java)
}