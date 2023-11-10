package com.mediatama.appsatipadang.teknik.ui_teknisi.Thome

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mediatama.appsatipadang.admin.ui.pengaduan_admin.fragment.DikerjakanTeknisiFragment
import com.mediatama.appsatipadang.admin.ui.pengaduan_admin.fragment.SelesaiTeknisiFragment
import com.mediatama.appsatipadang.teknik.ui_teknisi.penyerahan.PenyerahanFragment


private const val NUM_TABS = 3

class MenuTeknisiAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DikerjakanTeknisiFragment()
            1 -> SelesaiTeknisiFragment()
            2 -> PenyerahanFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}
