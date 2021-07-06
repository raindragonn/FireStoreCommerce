package com.raindragonn.firestorecommerce.ui.app_introduce

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import com.raindragonn.firestorecommerce.databinding.FragmentAppIntroduceBinding
import com.raindragonn.firestorecommerce.ui.base.BaseFragment
import com.raindragonn.firestorecommerce.util.isNetworkConnected
import com.raindragonn.firestorecommerce.util.showToast
import com.raindragonn.firestorecommerce.util.toGone
import com.raindragonn.firestorecommerce.util.toVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppIntroduceFragment : BaseFragment<FragmentAppIntroduceBinding>() {

    private val viewModel: AppIntroduceViewModel by viewModel()
    override fun getViewBinding(layoutInflater: LayoutInflater): FragmentAppIntroduceBinding {
        return FragmentAppIntroduceBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initViews()
        observeData()
        loadData()
    }

    private fun initViews() {
        binding.apply {
            swipeRefresh.setOnRefreshListener {
                loadData()
            }
        }
    }

    private fun observeData() {
        viewModel.apply {
            introText.observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    errorVisible()
                } else {
                    listVisible()
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        binding.tvIntroduce.text = Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        binding.tvIntroduce.text = Html.fromHtml(it)
                    }
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
            binding.tvIntroduce.toGone()
            binding.tvError.toVisible()
        }
    }

    private fun listVisible() {
        binding.apply {
            binding.tvIntroduce.toVisible()
            binding.tvError.toGone()
        }
    }


    private fun loadData() {
        try {
            if (requireContext().isNetworkConnected) {
                viewModel.getIntroText()
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