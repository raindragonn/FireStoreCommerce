package com.raindragonn.firestorecommerce.ui.product_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.raindragonn.firestorecommerce.databinding.FragmentProductListBinding
import com.raindragonn.firestorecommerce.ui.base.BaseFragment
import com.raindragonn.firestorecommerce.ui.main.MainFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : BaseFragment<FragmentProductListBinding>() {
    private val viewModel: ProductListViewModel by viewModel()

    override fun getViewBinding(layoutInflater: LayoutInflater): FragmentProductListBinding {
        return FragmentProductListBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnDetail.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToDetailFragment()
                findNavController().navigate(action)
            }
        }
    }
}