package vn.md18.fsquareapplication.features.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.md18.fsquareapplication.features.main.ui.fragment.OrderFragment
import vn.md18.fsquareapplication.features.main.ui.fragment.fragment_enum_order.EnumOrderFragment
import vn.md18.fsquareapplication.utils.OrderStatus

class OrderPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 5 // Số lượng tab

    override fun createFragment(position: Int): Fragment {
        val status = when (position) {
            0 -> OrderStatus.PENDING
            1 -> OrderStatus.PROCESSING
            2 -> OrderStatus.SHIPPED
            3 -> OrderStatus.DELIVERED
            4 -> OrderStatus.CANCELED
            else -> OrderStatus.PENDING
        }
        return EnumOrderFragment.newInstance(status)
    }
}

