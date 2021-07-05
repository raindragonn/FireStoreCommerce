package com.raindragonn.firestorecommerce.ui.product_introduce

import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.raindragonn.firestorecommerce.data.model.Product
import com.raindragonn.firestorecommerce.databinding.FragmentProductIntroduceBinding
import com.raindragonn.firestorecommerce.ui.base.BaseFragment
import jp.wasabeef.glide.transformations.BlurTransformation
import java.text.DecimalFormat

class ProductIntroduceFragment : BaseFragment<FragmentProductIntroduceBinding>() {
    companion object {
        fun newInstance(product: Product): ProductIntroduceFragment {
            return ProductIntroduceFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_BUNDLE_PRODUCT, product)
                }
            }
        }

        const val EXTRA_BUNDLE_PRODUCT = "EXTRA_BUNDLE_PRODUCT"
    }

    private val product: Product by lazy { arguments?.getParcelable(EXTRA_BUNDLE_PRODUCT)!! }

    override fun getViewBinding(layoutInflater: LayoutInflater): FragmentProductIntroduceBinding {
        return FragmentProductIntroduceBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            tvAuthor.text = product.author
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                tvTitle.text = Html.fromHtml(product.title, Html.FROM_HTML_MODE_COMPACT)
                tvDescription.text = Html.fromHtml(product.description, Html.FROM_HTML_MODE_COMPACT)
            } else {
                tvTitle.text = Html.fromHtml(product.title)
                tvDescription.text = Html.fromHtml(product.description)
            }

            product.totalReviewer?.let {
                if (it >= 1) {
                    val totalStar = product.totalStar ?: 0f
                    val totalReviewer = it
                    val star = totalStar/totalReviewer
                    tvStar.text = "%.2f".format(star)
                } else {
                    tvStar.text = "0"
                }
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
                SpannableStringBuilder("${product.publisher} | ${product.pubdate}").apply {
                    setSpan(
                        StyleSpan(Typeface.BOLD),
                        0,
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


            tvAuthor.text = authorSpan
            tvPublisher.text = publisherSpan
            tvPrice.text = priceSpan

            Glide.with(ivImage)
                .load(product.image)
                .thumbnail(0.1f)
                .into(ivImage)

            Glide.with(ivImageBackground)
                .load(product.image)
                .transform(CenterCrop(), BlurTransformation(5, 10))
                .into(ivImageBackground)
        }
    }
}