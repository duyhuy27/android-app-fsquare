package vn.md18.fsquareapplication.features.checkout.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.databinding.ItemTransactionHistoryBinding
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.vnpt.ONEHome.core.recycleview.BaseRecycleAdapter
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TransactionHistoryAdapter @Inject constructor() : BaseRecycleAdapter<GetOrderRespose>() {

    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemTransactionHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    inner class NormalViewHolder(viewBinding: ItemTransactionHistoryBinding) :
        BaseViewHolder<ItemTransactionHistoryBinding>(viewBinding) {

        @SuppressLint("SetTextI18n")
        override fun bindData(position: Int) {
            val product: GetOrderRespose = itemList[position]
            binding.apply {
                txtProductNameWallet.text = product.firstProduct.name
                txtProductPriceWallet.text = "${product.firstProduct.price} VND"

                val (formattedDate, formattedTime) = formatDateTime(product.createdAt)
                txtProductDateWallet.text = formattedDate
                txtProductTimeWallet.text = formattedTime
                Log.d("TransactionHistoryAdapter", "createdAt: ${product.createdAt}")
                imgItemWallet.loadImageURL(product.firstProduct.thumbnail?.url)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDateTime(isoDate: String?): Pair<String, String> {
        if (isoDate.isNullOrEmpty()) {
            return Pair("Invalid Date", "Invalid Time")
        }

        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            val outputFormatDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val outputFormatTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

            val date = inputFormat.parse(isoDate)
            val formattedDate = outputFormatDate.format(date!!)
            val formattedTime = outputFormatTime.format(date)

            Pair(formattedDate, formattedTime)
        } catch (e: Exception) {
            e.printStackTrace()
            Pair("Invalid Date", "Invalid Time")
        }
    }

}
