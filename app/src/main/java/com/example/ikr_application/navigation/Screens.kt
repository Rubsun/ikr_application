package com.example.ikr_application.navigation

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.ikr_application.R
import com.example.ikr_application.akiko23.ui.Akiko23Fragment
import com.example.ikr_application.dimmension.ui.NamesFragment
import com.example.ikr_application.nfirex.ui.MyFragment
import com.example.ikr_application.spl3g.ui.Spl3gFragment
import com.example.ikr_application.quovadis.ui.CatFragment
import com.example.ikr_application.zagora.ui.FragmentDog
import com.example.ikr_application.dimmension.ui.NamesFragment

enum class Screens(
    @StringRes val title: Int,
    val type: Class<out Fragment>,
) {
    SAMPLE(R.string.title_sample, MyFragment::class.java),
    STUPISHIN(
        R.string.title_stupishin,
        com.example.ikr_application.stupishin.ui.MyFragment::class.java
    ),
    NAMES(R.string.title_dimmension_screen, NamesFragment::class.java),
    AKIKO23(R.string.title_akiko23, Akiko23Fragment::class.java),
	SPL3G(R.string.title_spl3g, Spl3gFragment::class.java),
    QUOVADIS(R.string.title_quovadis, CatFragment::class.java)
    DENISOVA(R.string.title_denisova, com.example.ikr_application.denisova.ui.DenisovaFragment::class.java),
    GAF(R.string.dog, FragmentDog::class.java)

}