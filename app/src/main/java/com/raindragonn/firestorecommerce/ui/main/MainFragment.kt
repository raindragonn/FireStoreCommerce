package com.raindragonn.firestorecommerce.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.raindragonn.firestorecommerce.R
import com.raindragonn.firestorecommerce.databinding.FragmentMainBinding
import com.raindragonn.firestorecommerce.ui.app_introduce.AppIntroduceFragment
import com.raindragonn.firestorecommerce.ui.base.BaseFragment
import com.raindragonn.firestorecommerce.ui.product_list.ProductListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun getViewBinding(layoutInflater: LayoutInflater): FragmentMainBinding =
        FragmentMainBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.apply {
            viewPager.adapter = ViewPagerAdapter(this@MainFragment)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.title_product_list)
                    1 -> tab.text = getString(R.string.title_introduce)
                }
            }.attach()
        }
    }

    class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ProductListFragment()
                1 -> AppIntroduceFragment()
                else -> throw RuntimeException("ViewPagerAdapter Error - createFragment")
            }
        }
    }
}