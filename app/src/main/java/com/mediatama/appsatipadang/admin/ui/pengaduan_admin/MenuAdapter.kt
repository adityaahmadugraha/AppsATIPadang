package com.mediatama.appsatipadang.admin.ui.pengaduan_admin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mediatama.appsatipadang.admin.ui.pengaduan_admin.fragment.DikerjakanFragment
import com.mediatama.appsatipadang.admin.ui.pengaduan_admin.fragment.DiterimaFragment
import com.mediatama.appsatipadang.admin.ui.pengaduan_admin.fragment.FragmentDihapus
import com.mediatama.appsatipadang.admin.ui.pengaduan_admin.fragment.SelesaiFragment

private const val NUM_TABS = 4

class MenuAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return DiterimaFragment()
            1 -> return DikerjakanFragment()
            2 -> return SelesaiFragment()
        }
        return FragmentDihapus()
    }
}