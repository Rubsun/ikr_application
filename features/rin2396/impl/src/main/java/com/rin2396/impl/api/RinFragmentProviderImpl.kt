package com.rin2396.impl.api

import androidx.fragment.app.Fragment
import com.rin2396.impl.ui.CatsFragment

internal class RinFragmentProviderImpl : RinFragmentProvider {
    override fun getCatsFragment(): Fragment = CatsFragment()
}
