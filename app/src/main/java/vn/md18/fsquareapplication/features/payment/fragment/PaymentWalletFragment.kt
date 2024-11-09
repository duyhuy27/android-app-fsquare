package vn.md18.fsquareapplication.features.payment.fragment

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseFragment
import vn.md18.fsquareapplication.databinding.FragmentPaymentWalletBinding
import vn.md18.fsquareapplication.databinding.FragmentTransactionHistoryPaymentBinding
import vn.md18.fsquareapplication.features.main.adapter.OrderAdapter
import vn.md18.fsquareapplication.features.main.viewmodel.MainViewModel
import vn.md18.fsquareapplication.features.main.viewmodel.OrderViewModel
import vn.md18.fsquareapplication.utils.Constant
import vn.md18.fsquareapplication.utils.extensions.showCustomToast
import javax.inject.Inject

@AndroidEntryPoint
class PaymentWalletFragment : BaseFragment<FragmentPaymentWalletBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()
    @Inject
    lateinit var orderAdapter: OrderAdapter

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentPaymentWalletBinding = FragmentPaymentWalletBinding.inflate(layoutInflater)

    override fun getTagFragment(): String = FragmentPaymentWalletBinding::class.java.simpleName

    override fun onViewLoaded() {

        binding.apply {
            radioVietQr.isChecked = true
            radioApplePay.isChecked = false
            radioMasterCard.isChecked = false
            radioViza.isChecked = false
        }
    }

    override fun addViewListener() {
        binding.apply {
            radioVietQr.isChecked = true
            radioApplePay.isChecked = false
            radioMasterCard.isChecked = false
            radioViza.isChecked = false

            radioVietQr.setOnClickListener {
                radioVietQr.isChecked = true
                radioApplePay.isChecked = false
                radioMasterCard.isChecked = false
                radioViza.isChecked = false
            }
            radioApplePay.setOnClickListener {
                radioVietQr.isChecked = false
                radioApplePay.isChecked = true
                radioMasterCard.isChecked = false
                radioViza.isChecked = false
            }
            radioMasterCard.setOnClickListener {
                radioVietQr.isChecked = false
                radioApplePay.isChecked = false
                radioMasterCard.isChecked = true
                radioViza.isChecked = false
            }
            radioViza.setOnClickListener {
                radioVietQr.isChecked = false
                radioApplePay.isChecked = false
                radioMasterCard.isChecked = false
                radioViza.isChecked = true
            }

            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.radio_viet_qr -> {
                        radioApplePay.isChecked = false
                        radioMasterCard.isChecked = false
                        radioViza.isChecked = false
                    }
                    R.id.radio_apple_pay -> {
                        radioVietQr.isChecked = false
                        radioMasterCard.isChecked = false
                        radioViza.isChecked = false
                    }
                    R.id.radio_master_card -> {
                        radioVietQr.isChecked = false
                        radioApplePay.isChecked = false
                        radioViza.isChecked = false
                    }
                    R.id.radio_viza -> {
                        radioVietQr.isChecked = false
                        radioApplePay.isChecked = false
                        radioMasterCard.isChecked = false
                    }
                }
            }

            btnConfirmPayment.setOnClickListener {
                val selectedRadioButton = when (radioGroup.checkedRadioButtonId) {
                    R.id.radio_viet_qr -> radioVietQr
                    R.id.radio_apple_pay -> radioApplePay
                    R.id.radio_master_card -> radioMasterCard
                    R.id.radio_viza -> radioViza
                    else -> null
                }

                selectedRadioButton?.let {
                    val paymentMethod = when (it.id) {
                        R.id.radio_viet_qr -> Constant.VIET_QR
                        R.id.radio_apple_pay -> Constant.APPLE_PAY
                        R.id.radio_master_card -> Constant.MASTER_CARD
                        R.id.radio_viza -> Constant.VISA
                        else -> ""
                    }

                    val intent = Intent(context, OTPPaymentFragment::class.java).apply {
                        putExtra(Constant.PAYMENT_METHOD, paymentMethod)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    override fun addDataObserver() {

    }
}