package vn.md18.fsquareapplication.features.main.ui.fragment
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentOrderBinding
import vn.md18.fsquareapplication.features.main.adapter.OrderPagerAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding, MainViewModel>() {

    override val viewModel: MainViewModel by activityViewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentOrderBinding =
        FragmentOrderBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = OrderFragment::class.java.simpleName

    override fun onViewLoaded() {
        val tabTitles = listOf("Pending", "Processing", "Shipped", "Delivered", "Canceled")

        val adapter = OrderPagerAdapter(this)
        binding.viewPager.adapter = adapter

        val tabLayout = binding.horizontalScrollView.getChildAt(0) as LinearLayout
        for (i in tabTitles.indices) {
            val tabTextView = TextView(context).apply {
                text = tabTitles[i]
                setPadding(16, 16, 16, 16)
                setTextColor(Color.BLACK)
                textSize = 18f
                setTypeface(null, Typeface.NORMAL)

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.width = 500
                layoutParams = params
                gravity = android.view.Gravity.CENTER
            }

            val underlineView = View(context).apply {
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    9
                )
                params.setMargins(0, 15, 0, 0)
                layoutParams = params
                setBackgroundColor(Color.parseColor("#ebe6ef"))
            }


            val tabContainer = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(tabTextView)
                addView(underlineView)
            }

            tabLayout.addView(tabContainer)
            tabTextView.setOnClickListener {
                binding.viewPager.currentItem = i
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                highlightTab(position)
            }
        })
    }

    private fun highlightTab(position: Int) {
        val tabLayout = binding.horizontalScrollView.getChildAt(0) as LinearLayout
        for (i in 0 until tabLayout.childCount) {
            val tabContainer = tabLayout.getChildAt(i) as LinearLayout
            val tabTextView = tabContainer.getChildAt(0) as TextView
            val underlineView = tabContainer.getChildAt(1) as View

            tabTextView.setTextColor(Color.BLACK)
            tabTextView.setTypeface(null, Typeface.NORMAL)
            underlineView.setBackgroundColor(Color.parseColor("#ebe6ef"))
            underlineView.layoutParams.height = 9
        }

        val selectedTabContainer = tabLayout.getChildAt(position) as LinearLayout
        val selectedTabTextView = selectedTabContainer.getChildAt(0) as TextView
        val selectedUnderlineView = selectedTabContainer.getChildAt(1) as View

        selectedTabTextView.setTextColor(Color.BLACK)
        selectedTabTextView.setTypeface(null, Typeface.BOLD)
        selectedUnderlineView.setBackgroundColor(Color.parseColor("#FF8C00"))
        selectedUnderlineView.layoutParams.height = 12
    }

    override fun addViewListener() {}

    override fun addDataObserver() {}

    companion object {
        @JvmStatic
        fun newInstance() = OrderFragment()
    }
}

