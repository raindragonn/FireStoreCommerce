package com.raindragonn.firestorecommerce.ui.app_introduce

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.raindragonn.firestorecommerce.databinding.FragmentAppIntroduceBinding
import com.raindragonn.firestorecommerce.ui.base.BaseFragment

class AppIntroduceFragment : BaseFragment<FragmentAppIntroduceBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): FragmentAppIntroduceBinding {
        return FragmentAppIntroduceBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}