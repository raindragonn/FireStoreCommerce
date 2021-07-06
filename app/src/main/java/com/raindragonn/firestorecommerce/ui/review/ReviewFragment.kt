package com.raindragonn.firestorecommerce.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.raindragonn.firestorecommerce.data.model.Product
import com.raindragonn.firestorecommerce.data.model.Review
import com.raindragonn.firestorecommerce.databinding.DialogReviewBinding
import com.raindragonn.firestorecommerce.databinding.FragmentReviewBinding
import com.raindragonn.firestorecommerce.ui.base.BaseFragment
import com.raindragonn.firestorecommerce.ui.product_introduce.ProductIntroduceFragment
import com.raindragonn.firestorecommerce.util.isNetworkConnected
import com.raindragonn.firestorecommerce.util.showToast
import com.raindragonn.firestorecommerce.util.toGone
import com.raindragonn.firestorecommerce.util.toVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ReviewFragment : BaseFragment<FragmentReviewBinding>() {
    companion object {
        fun newInstance(product: Product): ReviewFragment {
            return ReviewFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_BUNDLE_PRODUCT, product)
                }
            }
        }

        const val EXTRA_BUNDLE_PRODUCT = "EXTRA_BUNDLE_PRODUCT"
    }

    private val viewModel: ReviewViewModel by viewModel {
        parametersOf(
            arguments?.getParcelable(
                ProductIntroduceFragment.EXTRA_BUNDLE_PRODUCT
            )!!
        )
    }

    override fun getViewBinding(layoutInflater: LayoutInflater): FragmentReviewBinding {
        return FragmentReviewBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
        loadData()
    }

    private fun initViews() {
        binding.apply {
            rv.adapter = ReviewListAdapter()
            rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (((rv.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                                == (rv.adapter as ReviewListAdapter).itemCount - 1) &&
                        (rv.adapter as ReviewListAdapter).itemCount >= 5
                    ) {
                        loadData()
                    }
                }
            })
            tvError.setOnClickListener { loadData() }
            btnReview.setOnClickListener { reviewWrite() }
        }
    }

    private fun reviewWrite() {
        val dialogBinding: DialogReviewBinding

        MaterialAlertDialogBuilder(requireContext())
            .setView(DialogReviewBinding.inflate(layoutInflater).also {
                dialogBinding = it
            }.root)
            .setPositiveButton("확인") { _, _ ->
                viewModel.writeReView(
                    dialogBinding.etReview.text.toString(),
                    dialogBinding.ratingBar.rating
                )
            }
            .setNegativeButton("취소") { _, _ ->

            }
            .show()
    }


    private fun removeReview(review: Review) {
        viewModel.removeReview(review)
    }

    private fun loadData() {
        try {
            if (requireContext().isNetworkConnected) {
                viewModel.getMoreList()
            } else {
                requireContext().showToast("인터넷 연결을 확인해주세요.")
                errorVisible()
            }
        } catch (e: Exception) {
            errorVisible()
        }
    }

    private fun observeData() {
        viewModel.apply {
            user.observe(viewLifecycleOwner) { user ->
                (binding.rv.adapter as? ReviewListAdapter)?.let {
                    it.userId = user.id
                }
            }

            reviewList.observe(viewLifecycleOwner) { list ->
                if (list.isNotEmpty()) {
                    listVisible()
                    (binding.rv.adapter as? ReviewListAdapter)?.let {
                        it.reviewDeleteClickListener = { review ->
                            this@ReviewFragment.removeReview(review)
                        }
                        it.submitList(list)
                    }
                } else {
                    errorVisible()
                }
            }

            isLoading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.pbLoading.toVisible()
                } else {
                    binding.pbLoading.toGone()
                }
            }
        }
    }

    private fun errorVisible() {
        binding.apply {
            binding.rv.toGone()
            binding.tvError.toVisible()
        }
    }

    private fun listVisible() {
        binding.apply {
            binding.rv.toVisible()
            binding.tvError.toGone()
        }
    }

}