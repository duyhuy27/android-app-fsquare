package vn.md18.fsquareapplication.features.detail.adapter

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.recyclerview.BaseRecycleAdapter
import vn.md18.fsquareapplication.data.network.model.response.ClassificationShoes
import vn.md18.fsquareapplication.data.network.model.response.GetReviewResponse
import vn.md18.fsquareapplication.databinding.ItemReviewShoesBinding
import vn.md18.fsquareapplication.databinding.ItemSizeDatailProduxBinding
import vn.md18.fsquareapplication.utils.extensions.loadImageURL
import vn.md18.fsquareapplication.utils.extensions.loadImageUri
import vn.vnpt.ONEHome.core.recycleview.BaseViewHolder
import java.util.Locale
import javax.inject.Inject

class ReviewAdapter @Inject constructor() : BaseRecycleAdapter<GetReviewResponse>(){
    override fun setLoadingViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
    return null
}

    override fun setEmptyViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return null
    }

    override fun setNormalViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        return NormalViewHolder(
            ItemReviewShoesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun setErrorViewHolder(parent: ViewGroup): BaseViewHolder<*>? {
        TODO("Not yet implemented")
    }

    inner class NormalViewHolder(binding: ItemReviewShoesBinding) :
        BaseViewHolder<ItemReviewShoesBinding>(binding) {

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        override fun bindData(position: Int) {
            val response: GetReviewResponse = itemList[position]
            binding.apply {
                txtUsername.text = "${response.customer.firstName} ${response.customer.lastName}"
                txtContent.text = response.content

                try {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                    val outputFormat = SimpleDateFormat("dd/MM/yyyy  HH:mm", Locale.getDefault())
                    val date = inputFormat.parse(response.createdAt)
                    txtCreateAt.text = date?.let { outputFormat.format(it) } ?: "N/A"
                } catch (e: Exception) {
                    e.printStackTrace()
                    txtCreateAt.text = "N/A"
                }

                imgAvatar.loadImageURL(response.customer.avatar?.url)

                val rating = response.rating.toFloat()
                val starImages = listOf(img1Rating, img2Rating, img3Rating, img4Rating, img5Rating)

                for (i in starImages.indices) {
                    when {
                        i + 1 <= rating -> {
                            starImages[i].setImageResource(R.drawable.star_more)
                            starImages[i].setPadding(2, 2, 2, 2)
                        }
                        i + 0.5 <= rating -> {
                            starImages[i].setImageResource(R.drawable.half_star)
                        }
                        else -> {
                            starImages[i].setImageResource(R.drawable.outline_star)

                        }
                    }
                }

                val mediaList = ((response.images ?: emptyList()) + (response.videos ?: emptyList())).take(4)
                if (mediaList.isNotEmpty()) {
                    mediaContainer.visibility = View.VISIBLE
                    val imageViews = listOf(imgReview1, imgReview2, imgReview3, imgReview4)
                    imageViews.forEach { it.visibility = View.GONE }

                    for (i in mediaList.indices) {
                        val media = mediaList[i]
                        val imageView = imageViews[i]
                        val mediaUrl = media?.url

                        if (!mediaUrl.isNullOrEmpty()) {
                            if (mediaUrl.contains("images")) {
                                imageView.loadImageURL(mediaUrl)
                            } else if (mediaUrl.contains("videos")) {
                                imageView.loadVideoThumbnail(mediaUrl)
                            }
                            imageView.visibility = View.VISIBLE
                        }
                    }
                } else {
                    mediaContainer.visibility = View.GONE
                }


            }
        }

    }

    fun ImageView.loadVideoThumbnail(videoUrl: String) {
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(videoUrl, HashMap())
            val bitmap = retriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
            this.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
            this.setImageResource(R.drawable.shoes)
        } finally {
            retriever.release()
        }
    }

}