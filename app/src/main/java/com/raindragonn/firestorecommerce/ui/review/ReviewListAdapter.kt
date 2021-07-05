package com.raindragonn.firestorecommerce.ui.review

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raindragonn.firestorecommerce.data.model.Review
import com.raindragonn.firestorecommerce.databinding.ItemReviewBinding
import com.raindragonn.firestorecommerce.util.toGone
import com.raindragonn.firestorecommerce.util.toVisible
import java.text.DecimalFormat

class ReviewListAdapter(
) : ListAdapter<Review, ReviewListAdapter.ReviewListViewHolder>(differ) {
    companion object {
        private val differ = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean =
                oldItem == newItem
        }
    }

    var userId: String? = null
    var reviewDeleteClickListener: ((Review) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewListViewHolder {
        return ReviewListViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReviewListViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tvRemove.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val review = getItem(adapterPosition)
                    if (userId == review.userId) {
                        reviewDeleteClickListener?.invoke(review)
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(review: Review) {
            binding.apply {
                tvContent.text = review.content

                val star: String = DecimalFormat("###,###").format(review.star)
                tvStar.text = star
                tvUser.text = "${review.userId?.take(3)}***"

                if (userId == review.userId) {
                    tvRemove.toVisible()
                } else {
                    tvRemove.toGone()
                }
            }
        }
    }
}