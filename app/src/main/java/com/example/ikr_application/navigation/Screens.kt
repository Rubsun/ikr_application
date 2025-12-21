package com.example.ikr_application.navigation

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.ikr_application.R
import com.example.ikr_application.akiko23.ui.Akiko23Fragment
import com.example.ikr_application.dimmension.ui.NamesFragment
import com.example.ikr_application.spl3g.ui.AppleFramesFragment
import com.alexcode69.api.Constants as Alexcode69Constants
import com.example.ikr_application.demyanenko.ui.F1CarFragment
import com.example.ikr_application.kristevt.ui.KristevtFragment
import com.n0tsszzz.api.Constants as N0tsszzzConstants
import com.example.ikr_application.tire.ui.MyFragment as TiReFragment
import com.rubsun.api.Constants as RubsunConstants
import com.rubsun.impl.R as rubsunRes
import com.rin2396.api.Constants as RinApi
import com.example.ikr_application.eremin.ui.CapybaraFragment
import com.example.ikr_application.vtyapkova.ui.ViktoriaFragment
import com.dyatlova.api.Constants as DyatlovaConstants
import com.dyatlova.impl.R as dyatlovaRes
import com.grigoran.api.Constants.GRIGORAN_SCREEN
import quo.vadis.api.Constants.QUOVADIS_SCREEN
import com.nfirex.api.Constants
import com.denisova.api.Constants as DenisovaConstants
import com.nfirex.impl.R as nfirexRes
import com.nastyazz.api.Constants as NastyazzConstants
import com.stupishin.api.Constants as StupishinConstants
import com.stupishin.impl.R as stupishinRes
import com.artemkaa.api.Constants as ArtemkaaConstants
import com.antohaot.api.Constants as AntohaotConstants
import com.drain678.api.Constants as Drain678Constants
import com.denisova.impl.R as denisovaRes
import com.zagora.api.Constants as ZagoraConstants
import com.momuswinner.api.Constants as MomusWinnerConstants
import com.momuswinner.impl.R as MomusWinnerRes
import com.egorik4.api.Constants as Egorik4Constants
import com.egorik4.impl.R as egorik4Res

enum class Screens(
    @StringRes val title: Int,
    val type: Class<out Fragment>? = null,
    val qualifier: String? = null,
) {
    SAMPLE(nfirexRes.string.title_sample, qualifier = Constants.NFIREX_SCREEN),
    TIRE(R.string.title_tire, TiReFragment::class.java),
    STUPISHIN(stupishinRes.string.title_stupishin, qualifier = StupishinConstants.STUPISHIN_SCREEN),
    NAMES(R.string.title_dimmension_screen, NamesFragment::class.java),
    SPL3G(R.string.title_spl3g, AppleFramesFragment::class.java),
    RIN2396(R.string.title_rin2396, qualifier = RinApi.SCREEN),
    AKIKO23(R.string.title_akiko23, Akiko23Fragment::class.java),
    QUOVADIS(R.string.title_quovadis, qualifier = QUOVADIS_SCREEN),
    DEMYANENKO(R.string.title_demyanenko, F1CarFragment::class.java),
    DENISOVA(denisovaRes.string.title_denisova, qualifier = DenisovaConstants.DENISOVA_SCREEN),
    ARTEMKAA(R.string.title_artemkaa, qualifier = ArtemkaaConstants.ARTEMKAA_SCREEN),
    ANTOHAOT(R.string.title_antohaot, qualifier = AntohaotConstants.ANTOHAOT_SCREEN),
    DRAIN678(R.string.title_drain678, qualifier = Drain678Constants.DRAIN678_SCREEN),
    ALEXCODE69(R.string.title_alexcode69, qualifier = Alexcode69Constants.ALEXCODE69_SCREEN),
    KRISTEVT(R.string.title_kristevt, KristevtFragment::class.java),
    N0TSSZZZ(R.string.title_n0tsSzzz, qualifier = N0tsszzzConstants.N0TSSZZZ_SCREEN),
    RUBSUN(rubsunRes.string.title_rubsun, qualifier = RubsunConstants.RUBSUN_SCREEN),
    MICHAELNOSKOV(R.string.title_michaelnoskov, com.example.ikr_application.michaelnoskov.ui.fragment.ColorSquareFragment::class.java),
    NASTYAZZ(R.string.title_nastyazz, qualifier = NastyazzConstants.NASTYAZZ_SCREEN),
    GRIGORAN(R.string.title_grigoran, qualifier = GRIGORAN_SCREEN),
    VTYAPKOVA(R.string.title_viktoria, ViktoriaFragment::class.java),
    EGORIK4(egorik4Res.string.title_egorik4_screen, qualifier = Egorik4Constants.EGORIK4_SCREEN),
    ZAGORA(R.string.title_zagora, qualifier = ZagoraConstants.ZAGORA_SCREEN),
    AREG(MomusWinnerRes.string.areg_title, qualifier = MomusWinnerConstants.MOMUS_WINNER_SCREEN),
    EREMIN(R.string.title_eremin, CapybaraFragment::class.java),
    DYATLOVA(dyatlovaRes.string.dyatlova_title, qualifier = DyatlovaConstants.DYATLOVA_SCREEN)
}
