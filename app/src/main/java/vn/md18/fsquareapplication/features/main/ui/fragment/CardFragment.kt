package vn.md18.fsquareapplication.features.main.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.databinding.DialogConfirmDeleteFavBinding
import vn.md18.fsquareapplication.databinding.FragmentCardBinding
import vn.md18.fsquareapplication.features.checkout.ui.CheckoutActivity
import vn.md18.fsquareapplication.features.detail.ui.DetailProductActivity
import vn.md18.fsquareapplication.features.main.adapter.BagAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.BagViewmodel
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.profileandsetting.ui.ProfileAndSettingActivity
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@AndroidEntryPoint
class CardFragment : BaseFragment<FragmentCardBinding, BagViewmodel>(), BagAdapter.OnBagActionListener {

    @Inject
    lateinit var bagAdapter: BagAdapter
    override val viewModel: BagViewmodel by  activityViewModels()
    private lateinit var launcher: ActivityResultLauncher<Intent>
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentCardBinding  = FragmentCardBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = CardFragment::class.java.simpleName

    override fun onViewLoaded() {
        bagAdapter.setBagActionListener(this)
        viewModel.getBagList()

        binding.apply {
            rcvProductCart.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = bagAdapter
            }
        }
    }

    override fun addViewListener() {
        binding.apply {
            btnCheckout.setOnClickListener {
                val intent = Intent(requireContext(), CheckoutActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun addDataObserver() {
        viewModel.listBag.observe(this@CardFragment) {
            binding.apply {
                bagAdapter.submitList(it)
                FSLogger.e("cart enum : $it")
            }
            if (!it.isNullOrEmpty()) {
                val totalPrice = it.sumOf { item -> item.price * item.quantity }
                binding.btnCheckout.text = "${totalPrice} ${getString(R.string.Checkuot)}"
            } else {
                binding.btnCheckout.text = "0 VND ${getString(R.string.Checkuot)}"
            }
        }
        viewModel.updateQuantityState.observe(this@CardFragment){
            if(it is DataState.Success){
                viewModel.getBagList()
            }
        }

        viewModel.deleteBagByIdState.observe(this@CardFragment){
            if(it is DataState.Success){
                viewModel.getBagList()
                activity?.showCustomToast("Xóa sản phẩm thành công", Constant.ToastStatus.SUCCESS)
            }else{
                activity?.showCustomToast("Xóa sản phẩm thất bại", Constant.ToastStatus.SUCCESS)
            }
        }

        viewModel.shouldReloadCart.observe(this) { shouldReload ->
            if (shouldReload == true) {
                viewModel.getBagList()
                viewModel.shouldReloadCart.value = false
            }
        }

    }
    companion object {
        @JvmStatic
        fun newInstance() = CardFragment()
    }

    override fun onRemoveBag(productId: String) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_confirm_delete_fav, null)
        val binding = DialogConfirmDeleteFavBinding.bind(dialogView)

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        binding.txtTitle.text = getString(R.string.Delete_Cart)
        binding.txtContent.text = getString(R.string.Text_confirm_delete_cart)

        binding.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            viewModel.deleteBagById(productId)
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    override fun onUpdateQuantityBag(productId: String, action: String) {
        viewModel.updateQuantity(productId, action)
        viewModel.listBag.value?.let {
            val totalPrice = it.sumOf { item -> item.price * item.quantity }
            binding.btnCheckout.text = "${totalPrice} ${getString(R.string.Checkuot)}"
        }
    }

}