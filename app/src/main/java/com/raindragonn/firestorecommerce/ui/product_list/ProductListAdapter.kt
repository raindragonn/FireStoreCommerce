package com.raindragonn.firestorecommerce.ui.product_list

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raindragonn.firestorecommerce.data.model.Product
import com.raindragonn.firestorecommerce.databinding.ItemProductBinding
import com.raindragonn.firestorecommerce.util.showToast
import com.raindragonn.firestorecommerce.util.toGone
import com.raindragonn.firestorecommerce.util.toVisible
import java.text.DecimalFormat

class ProductListAdapter : ListAdapter<Product, ProductListAdapter.ProductListViewHolder>(differ) {
    companion object {
        private val differ = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }

    var itemClickListener: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder =
        ProductListViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductListViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val item = getItem(adapterPosition)
                if (item.isSelling == true) {
                    itemClickListener?.invoke(item)
                } else {
                    binding.root.context.showToast("해당 제품은 상품 준비중 입니다.")
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            binding.apply {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    tvTitle.text = Html.fromHtml(product.title, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    tvTitle.text = Html.fromHtml(product.title)
                }

                val authorSpan = SpannableStringBuilder("저자 : ${product.author}").apply {
                    setSpan(
                        StyleSpan(Typeface.BOLD),
                        2,
                        length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                val publisherSpan =
                    SpannableStringBuilder("출판 : ${product.publisher} | ${product.pubdate}").apply {
                        setSpan(
                            StyleSpan(Typeface.BOLD),
                            2,
                            length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }

                val formatPrice = DecimalFormat("###,###").format(product.price?.toInt())
                val priceSpan = SpannableStringBuilder("가격 : $formatPrice").apply {
                    setSpan(
                        StyleSpan(Typeface.BOLD),
                        2,
                        length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                product.totalReviewer?.let {
                    if (it >= 1) {
                        val totalStar = product.totalStar ?: 0f
                        val totalReviewer = it
                        val star = totalStar / totalReviewer
                        tvStar.text = "%.2f".format(star)
                    } else {
                        tvStar.text = "0"
                    }
                }

                tvAuthor.text = authorSpan
                tvPublisher.text = publisherSpan
                tvPrice.text = priceSpan

                Glide.with(ivImage)
                    .load(product.image)
                    .thumbnail(0.1f)
                    .into(ivImage)

                if (product.isSelling == false) {
                    flNotSelling.toVisible()
                } else {
                    flNotSelling.toGone()
                }
            }
        }

    }
}