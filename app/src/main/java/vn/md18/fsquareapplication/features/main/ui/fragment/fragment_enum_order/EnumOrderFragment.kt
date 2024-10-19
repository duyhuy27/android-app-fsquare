package vn.md18.fsquareapplication.features.main.ui.fragment.fragment_enum_order

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentEnumOrderBinding
import vn.md18.fsquareapplication.databinding.FragmentOrderBinding
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.utils.Constant

@AndroidEntryPoint
class EnumOrderFragment : BaseFragment<FragmentEnumOrderBinding, MainViewModel>() {
    override val viewModel: MainViewModel by activityViewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentEnumOrderBinding = FragmentEnumOrderBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = EnumOrderFragment::class.java.simpleName

    override fun onViewLoaded() {
        val status = arguments?.getString(ARG_STATUS) ?: Constant.PENDING

        when (status) {
            Constant.PENDING -> binding.tx1.text = "tab 1"
            Constant.PROCESSING -> binding.tx1.text = "tab 2"
            Constant.SHIPPED -> binding.tx1.text = "tab 3"
            Constant.DELIVERED -> binding.tx1.text = "tab 4"
            Constant.CANCELED -> binding.tx1.text = "tab 5"
        }
    }

    override fun addViewListener() {

    }

    override fun addDataObserver() {

    }
    companion object {
        private const val ARG_STATUS = "status"
        @JvmStatic
        fun newInstance(status: String): EnumOrderFragment {
            val fragment = EnumOrderFragment()
            val args = Bundle()
            args.putString(ARG_STATUS, status)
            fragment.arguments = args
            return fragment
        }
        @JvmStatic
        fun newInstance(): EnumOrderFragment {
            return EnumOrderFragment()
        }
    }

    private fun fetchPendingList(){
        Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
    }

    private fun fetchProcessingList(){
        Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
    }

    private fun fetchShippedList(){
        Toast.makeText(context, "3", Toast.LENGTH_SHORT).show()
    }

    private fun fetchDeliveredList(){
        Toast.makeText(context, "4", Toast.LENGTH_SHORT).show()
    }

    private fun fetchCanceledList(){
        Toast.makeText(context, "5", Toast.LENGTH_SHORT).show()
    }
}