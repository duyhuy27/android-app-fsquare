package vn.md18.fsquareapplication.features.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.md18.fsquareapplication.features.main.ui.CardFragment
import vn.md18.fsquareapplication.features.main.ui.DashboardFragment
import vn.md18.fsquareapplication.features.main.ui.OrderFragment
import vn.md18.fsquareapplication.features.main.ui.ProfileFragment
import vn.md18.fsquareapplication.features.main.ui.WalletFragment

class MainPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm.supportFragmentManager, fm.lifecycle) {

    /**
     * List Items Data
     */
    private var dashboardFragment: DashboardFragment = DashboardFragment.newInstance()
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
            0 -> dashboardFragment // devices and rooms
            1 -> cartFragment   // scene list
            2 -> orderFragment
            3 -> walletFragment
            4 -> profileFragment// notification list
            else -> Fragment()
        }
    }


}