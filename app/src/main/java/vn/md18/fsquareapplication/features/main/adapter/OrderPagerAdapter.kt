package vn.md18.fsquareapplication.features.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.md18.fsquareapplication.features.main.ui.fragment.OrderFragment
import vn.md18.fsquareapplication.features.main.ui.fragment.fragment_enum_order.EnumOrderFragment
import vn.md18.fsquareapplication.utils.OrderStatus

class OrderPagerAdapter(fragment: OrderFragment) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(
        EnumOrderFragment.newInstance(OrderStatus.PENDING),
        EnumOrderFragment.newInstance(OrderStatus.PROCESSING),
        EnumOrderFragment.newInstance(OrderStatus.SHIPPED),
        EnumOrderFragment.newInstance(OrderStatus.DELIVERED),
        EnumOrderFragment.newInstance(OrderStatus.CANCELED)
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
