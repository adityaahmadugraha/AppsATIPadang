package com.aditya.appsatipadang.teknik.ui_teknisi.Thome

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aditya.appsatipadang.admin.ui.pengaduan_admin.fragment.DikerjakanFragment
import com.aditya.appsatipadang.admin.ui.pengaduan_admin.fragment.SelesaiFragment


private const val NUM_TABS = 2
class MenuTeknisiAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return DikerjakanFragment()
        }
        return SelesaiFragment()
    }
}