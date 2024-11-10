package vn.md18.fsquareapplication.features.main.ui.fragment

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentCardBinding
import vn.md18.fsquareapplication.features.main.adapter.BagAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.BagViewmodel
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CardFragment : BaseFragment<FragmentCardBinding, BagViewmodel>(), BagAdapter.OnBagActionListener {

    @Inject
    lateinit var bagAdapter: BagAdapter
    override val viewModel: BagViewmodel by  activityViewModels()
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentCardBinding  = FragmentCardBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = CardFragment::class.java.simpleName

    override fun onViewLoaded() {
        bagAdapter.setBagActionListener(this)

        binding.apply {
            rcvProductCart.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = bagAdapter
            }
        }
    }

    override fun addViewListener() {
        viewModel.getBagList()
    }

    override fun addDataObserver() {
        viewModel.listBag.observe(this@CardFragment) {
            binding.apply {
                bagAdapter.submitList(it)
            }
            if (!it.isNullOrEmpty()) {
                val totalPrice = it.sumOf { item -> item.price * item.quantity }
                binding.btnCheckout.text = "${totalPrice} ${getString(R.string.Checkuot)}"
            } else {
                binding.btnCheckout.text = "0 VND ${getString(R.string.Checkuot)}"
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = CardFragment()
    }

    override fun onRemoveBag() {

    }

    override fun onUpdateQuantityBag(productId: String, action: String) {
        viewModel.updateQuantity(productId, action)
        viewModel.listBag.value?.let {
            val totalPrice = it.sumOf { item -> item.price * item.quantity }
            binding.btnCheckout.text = "${totalPrice} ${getString(R.string.Checkuot)}"
        }
    }

}