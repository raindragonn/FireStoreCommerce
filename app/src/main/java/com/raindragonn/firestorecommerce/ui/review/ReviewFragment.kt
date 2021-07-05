package com.raindragonn.firestorecommerce.ui.review

import android.view.LayoutInflater
import com.raindragonn.firestorecommerce.databinding.FragmentReviewBinding
import com.raindragonn.firestorecommerce.ui.base.BaseFragment

class ReviewFragment : BaseFragment<FragmentReviewBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): FragmentReviewBinding {
        return FragmentReviewBinding.inflate(layoutInflater)
    }
}