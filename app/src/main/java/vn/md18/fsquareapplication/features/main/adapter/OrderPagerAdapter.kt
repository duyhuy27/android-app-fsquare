package vn.md18.fsquareapplication.features.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.md18.fsquareapplication.features.main.ui.fragment.OrderFragment
import vn.md18.fsquareapplication.features.main.ui.fragment.fragment_enum_order.EnumOrderFragment

class OrderPagerAdapter(fragment: OrderFragment) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(
        EnumOrderFragment.newInstance("Pending"),
        EnumOrderFragment.newInstance("Processing"),
        EnumOrderFragment.newInstance("Shipped"),
        EnumOrderFragment.newInstance("Delivered"),
        EnumOrderFragment.newInstance("Canceled")
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
