package com.example.ikr_application.navigation

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.ikr_application.R
import com.akiko23.api.Constants as Akiko23Constants
import com.dimmension.api.Constants as DimmensionConstants
import com.dimmension.impl.R as dimmensionRes
import com.alexcode69.api.Constants as Alexcode69Constants
import com.n0tsszzz.api.Constants as N0tsszzzConstants
import com.tire.api.Constants.TIRE_SCREEN
import com.rubsun.api.Constants as RubsunConstants
import com.rubsun.impl.R as rubsunRes
import com.eremin.api.Constants as EreminConstants
import com.rin2396.api.Constants as RinApi
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
import com.example.api.Constants as DemyanenkoConstants
import com.spl3g.api.Constants as Spl3gConstants
import com.spl3g.impl.R as spl3gRes
import com.kristevt.api.Constants as KristevtConstants
import com.kristevt.impl.R as kristevtRes

enum class Screens(
    @StringRes val title: Int,
    val type: Class<out Fragment>? = null,
    val qualifier: String? = null,
) {
    SAMPLE(nfirexRes.string.title_sample, qualifier = Constants.NFIREX_SCREEN),
    TIRE(R.string.title_tire, qualifier = TIRE_SCREEN),
    STUPISHIN(stupishinRes.string.title_stupishin, qualifier = StupishinConstants.STUPISHIN_SCREEN),
    NAMES(dimmensionRes.string.title_dimmension_screen, qualifier = DimmensionConstants.DIMMENSION_SCREEN),
	SPL3G(spl3gRes.string.title_spl3g, qualifier = Spl3gConstants.SPL3G_SCREEN),
    DEMYANENKO(R.string.title_demyanenko, qualifier = DemyanenkoConstants.DEM_SCREEN),
    RIN2396(R.string.title_rin2396, qualifier = RinApi.SCREEN),
    AKIKO23(R.string.title_akiko23, qualifier = Akiko23Constants.AKIKO23_SCREEN),
    QUOVADIS(R.string.title_quovadis, qualifier = QUOVADIS_SCREEN),
    DENISOVA(denisovaRes.string.title_denisova, qualifier = DenisovaConstants.DENISOVA_SCREEN),
    ARTEMKAA(R.string.title_artemkaa, qualifier = ArtemkaaConstants.ARTEMKAA_SCREEN),
    ANTOHAOT(R.string.title_antohaot, qualifier = AntohaotConstants.ANTOHAOT_SCREEN),
    DRAIN678(R.string.title_drain678, qualifier = Drain678Constants.DRAIN678_SCREEN),
    ALEXCODE69(R.string.title_alexcode69, qualifier = Alexcode69Constants.ALEXCODE69_SCREEN),
    KRISTEVT(kristevtRes.string.title_kristevt, qualifier = KristevtConstants.KRISTEVT_SCREEN),
    N0TSSZZZ(R.string.title_n0tsSzzz, qualifier = N0tsszzzConstants.N0TSSZZZ_SCREEN),
    RUBSUN(rubsunRes.string.title_rubsun, qualifier = RubsunConstants.RUBSUN_SCREEN),
    MICHAELNOSKOV(R.string.title_michaelnoskov, com.example.ikr_application.michaelnoskov.ui.fragment.ColorSquareFragment::class.java),
    NASTYAZZ(R.string.title_nastyazz, qualifier = NastyazzConstants.NASTYAZZ_SCREEN),
    GRIGORAN(R.string.title_grigoran, qualifier = GRIGORAN_SCREEN),
    VTYAPKOVA(R.string.title_viktoria, ViktoriaFragment::class.java),
    EGORIK4(egorik4Res.string.title_egorik4_screen, qualifier = Egorik4Constants.EGORIK4_SCREEN),
    ZAGORA(R.string.title_zagora, qualifier = ZagoraConstants.ZAGORA_SCREEN),
    AREG(MomusWinnerRes.string.areg_title, qualifier = MomusWinnerConstants.MOMUS_WINNER_SCREEN),
    DYATLOVA(dyatlovaRes.string.dyatlova_title, qualifier = DyatlovaConstants.DYATLOVA_SCREEN),
    EREMIN(R.string.title_eremin, qualifier = EreminConstants.EREMIN_SCREEN)
}
