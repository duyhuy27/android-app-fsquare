package vn.md18.fsquareapplication.features.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.md18.fsquareapplication.features.main.ui.fragment.CardFragment
import vn.md18.fsquareapplication.features.main.ui.fragment.DashboardFragment
import vn.md18.fsquareapplication.features.main.ui.fragment.HomeFragment
import vn.md18.fsquareapplication.features.main.ui.fragment.OrderFragment
import vn.md18.fsquareapplication.features.main.ui.fragment.ProfileFragment
import vn.md18.fsquareapplication.features.main.ui.fragment.WalletFragment

class MainPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm.supportFragmentManager, fm.lifecycle) {

    /**
     * List Items Data
     */
    private var homeFragment: HomeFragment = HomeFragment.newInstance()
    private var cartFragment: CardFragment = CardFragment.newInstance()
    private var orderFragment: OrderFragment = OrderFragment.newInstance()
    private var walletFragment : WalletFragment = WalletFragment.newInstance()
    private var profileFragment: ProfileFragment = ProfileFragment.newInstance()

    /**
     * Return the number of views available.
     */
    override fun getItemCount(): Int {
        return 5
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> homeFragment // devices and rooms
            1 -> cartFragment   // scene list
            2 -> orderFragment
            3 -> walletFragment
            4 -> profileFragment// notification list
            else -> Fragment()
        }
    }


}