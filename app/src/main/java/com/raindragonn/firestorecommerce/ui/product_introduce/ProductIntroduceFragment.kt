package com.raindragonn.firestorecommerce.ui.product_introduce

import android.view.LayoutInflater
import com.raindragonn.firestorecommerce.databinding.FragmentProductIntroduceBinding
import com.raindragonn.firestorecommerce.ui.base.BaseFragment

class ProductIntroduceFragment : BaseFragment<FragmentProductIntroduceBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): FragmentProductIntroduceBinding {
        return FragmentProductIntroduceBinding.inflate(layoutInflater)
    }
}