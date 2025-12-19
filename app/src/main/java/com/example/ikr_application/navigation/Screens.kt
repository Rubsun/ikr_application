package com.example.ikr_application.navigation

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.ikr_application.MomusWinner.ui.GraphFragment
import com.example.ikr_application.R
import com.example.ikr_application.akiko23.ui.Akiko23Fragment
import com.example.ikr_application.dimmension.ui.NamesFragment
import com.example.ikr_application.spl3g.ui.AppleFramesFragment
import com.example.ikr_application.rin2396.ui.RinFragment
import com.example.ikr_application.antohaot.ui.AntohaotFragment
import com.example.ikr_application.alexcode69.ui.Alexcode69Fragment
import com.example.ikr_application.demyanenko.ui.F1CarFragment
import com.example.ikr_application.kristevt.ui.KristevtFragment
import com.n0tsszzz.api.Constants as N0tsszzzConstants
import com.example.ikr_application.tire.ui.MyFragment as TiReFragment
import com.example.ikr_application.drain678.ui.Drain678Fragment
import com.example.ikr_application.quovadis.ui.CatFragment
import com.example.ikr_application.rubsun.ui.NumberFragment
import com.example.ikr_application.egorik4.ui.Egorik4Fragment
import com.example.ikr_application.eremin.ui.CapybaraFragment
import com.example.ikr_application.vtyapkova.ui.ViktoriaFragment
import com.example.ikr_application.zagora.ui.FragmentDog
import com.example.ikr_application.dyatlova.ui.DyatlovaFragment
import com.grigoran.api.Constants.GRIGORAN_SCREEN
import com.nfirex.api.Constants
import com.nfirex.impl.R as nfirexRes
import com.nastyazz.api.Constants as NastyazzConstants
import com.stupishin.api.Constants as StupishinConstants
import com.stupishin.impl.R as stupishinRes
import com.artemkaa.api.Constants as ArtemkaaConstants

enum class Screens(
    @param:StringRes val title: Int,
    val type: Class<out Fragment>? = null,
    val qualifier: String? = null,
) {
    SAMPLE(nfirexRes.string.title_sample, qualifier = Constants.NFIREX_SCREEN),
    TIRE(R.string.title_tire, TiReFragment::class.java),
    STUPISHIN(stupishinRes.string.title_stupishin, qualifier = StupishinConstants.STUPISHIN_SCREEN),
    NAMES(R.string.title_dimmension_screen, NamesFragment::class.java),
    SPL3G(R.string.title_spl3g, AppleFramesFragment::class.java),
    RIN2396(R.string.title_rin2396, RinFragment::class.java),
    AKIKO23(R.string.title_akiko23, Akiko23Fragment::class.java),
    QUOVADIS(R.string.title_quovadis, CatFragment::class.java),
    DEMYANENKO(R.string.title_demyanenko, F1CarFragment::class.java),
    DENISOVA(R.string.title_denisova, com.example.ikr_application.denisova.ui.DenisovaFragment::class.java),
    ARTEMKAA(R.string.title_artemkaa, qualifier = ArtemkaaConstants.ARTEMKAA_SCREEN),
    ANTOHAOT(R.string.title_antohaot, AntohaotFragment::class.java),
    ALEXCODE69(R.string.title_alexcode69, Alexcode69Fragment::class.java),
    KRISTEVT(R.string.title_kristevt, KristevtFragment::class.java),
    N0TSSZZZ(R.string.title_n0tsSzzz, qualifier = N0tsszzzConstants.N0TSSZZZ_SCREEN),
    RUBSUN(R.string.title_rubsun, NumberFragment::class.java),
    DRAIN678(R.string.title_drain678, Drain678Fragment::class.java),
    MICHAELNOSKOV(R.string.title_michaelnoskov, com.example.ikr_application.michaelnoskov.ui.fragment.ColorSquareFragment::class.java),
    NASTYAZZ(R.string.title_nastyazz, qualifier = NastyazzConstants.NASTYAZZ_SCREEN),
    GRIGORAN(R.string.title_grigoran, qualifier = GRIGORAN_SCREEN),
    VTYAPKOVA(R.string.title_viktoria, ViktoriaFragment::class.java),
    EGORIK4(R.string.title_egorik4, Egorik4Fragment::class.java),
    ZAGORA(R.string.title_zagora, FragmentDog::class.java),
    AREG(R.string.title_areg, GraphFragment::class.java),
    EREMIN(R.string.title_eremin, CapybaraFragment::class.java),
    DYATLOVA(R.string.title_dyatlova, DyatlovaFragment::class.java)
}
