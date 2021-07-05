package com.raindragonn.firestorecommerce.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.raindragonn.firestorecommerce.R
import com.raindragonn.firestorecommerce.databinding.FragmentDetailBinding
import com.raindragonn.firestorecommerce.ui.base.BaseFragment
import com.raindragonn.firestorecommerce.ui.product_introduce.ProductIntroduceFragment
import com.raindragonn.firestorecommerce.ui.review.ReviewFragment

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): FragmentDetailBinding =
        FragmentDetailBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewPager.adapter = ViewPagerAdapter(this@DetailFragment)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.title_product_introduce)
                    1 -> tab.text = getString(R.string.title_review)
                }
            }.attach()
        }
    }

    class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ProductIntroduceFragment()
                1 -> ReviewFragment()
                else -> throw RuntimeException("ViewPagerAdapter Error - createFragment")
            }
        }
    }
}