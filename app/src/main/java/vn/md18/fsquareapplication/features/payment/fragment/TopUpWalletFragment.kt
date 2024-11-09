package vn.md18.fsquareapplication.features.payment.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentPaymentWalletBinding
import vn.md18.fsquareapplication.databinding.FragmentTopUpWalletBinding
import vn.md18.fsquareapplication.features.main.adapter.OrderAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.utils.Constant
import javax.inject.Inject

class TopUpWalletFragment : BaseFragment<FragmentTopUpWalletBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()
    @Inject
    lateinit var orderAdapter: OrderAdapter

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentTopUpWalletBinding = FragmentTopUpWalletBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = TopUpWalletFragment::class.java.simpleName

    override fun onViewLoaded() {
    }

    override fun addViewListener() {
        binding.apply {
            btn10k.setOnClickListener {
                edtMonyPayment.setText("10.000")
            }
            btn20k.setOnClickListener {
                edtMonyPayment.setText("20.000")
            }
            btn50k.setOnClickListener {
                edtMonyPayment.setText("50.000")
            }
            btn100k.setOnClickListener {
                edtMonyPayment.setText("100.000")
            }
            btn200k.setOnClickListener {
                edtMonyPayment.setText("200.000")
            }
            btn300k.setOnClickListener {
                edtMonyPayment.setText("300.000")
            }
            btn500k.setOnClickListener {
                edtMonyPayment.setText("500.000")
            }
            btn1m.setOnClickListener {
                edtMonyPayment.setText("1.000.000")
            }
            btn2m.setOnClickListener {
                edtMonyPayment.setText("2.000.000")
            }
        }
    }

    override fun addDataObserver() {

    }
}