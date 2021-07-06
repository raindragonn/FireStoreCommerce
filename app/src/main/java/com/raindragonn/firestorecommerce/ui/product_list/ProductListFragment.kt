package com.raindragonn.firestorecommerce.ui.product_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.raindragonn.firestorecommerce.databinding.FragmentProductListBinding
import com.raindragonn.firestorecommerce.ui.base.BaseFragment
import com.raindragonn.firestorecommerce.ui.main.MainFragmentDirections
import com.raindragonn.firestorecommerce.util.isNetworkConnected
import com.raindragonn.firestorecommerce.util.showToast
import com.raindragonn.firestorecommerce.util.toGone
import com.raindragonn.firestorecommerce.util.toVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : BaseFragment<FragmentProductListBinding>() {
    private val viewModel: ProductListViewModel by viewModel()

    override fun getViewBinding(layoutInflater: LayoutInflater): FragmentProductListBinding {
        return FragmentProductListBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
        loadData()
    }

    private fun initViews() {
        binding.apply {
            rv.adapter = ProductListAdapter()

            swipeRefresh.setOnRefreshListener {
                loadData()
            }
        }
    }

    private fun observeData() {
        viewModel.apply {
            productList.observe(viewLifecycleOwner) { produts ->
                if (produts.isNotEmpty()) {
                    listVisible()
                    (binding.rv.adapter as? ProductListAdapter)?.let {
                        it.itemClickListener = { product ->
                            val action =
                                MainFragmentDirections.actionMainFragmentToDetailFragment(product)
                            findNavController().navigate(action)
                        }

                        it.submitList(produts)
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


    private fun loadData() {
        try {
            if (requireContext().isNetworkConnected) {
                viewModel.getProductData()
            } else {
                requireContext().showToast("인터넷 연결을 확인해주세요.")
                errorVisible()
            }
        } catch (e: Exception) {
            errorVisible()
        } finally {
            binding.swipeRefresh.isRefreshing = false
        }
    }
}