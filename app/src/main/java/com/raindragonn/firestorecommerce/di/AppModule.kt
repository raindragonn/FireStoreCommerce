package com.raindragonn.firestorecommerce.di

import com.raindragonn.firestorecommerce.ui.app_introduce.AppIntroduceViewModel
import com.raindragonn.firestorecommerce.ui.detail.DetailViewModel
import com.raindragonn.firestorecommerce.ui.main.MainViewModel
import com.raindragonn.firestorecommerce.ui.product_introduce.ProductIntroduceViewModel
import com.raindragonn.firestorecommerce.ui.product_list.ProductListViewModel
import com.raindragonn.firestorecommerce.ui.review.ReviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //ViewModel
    viewModel<MainViewModel> { MainViewModel() }
    viewModel<ProductListViewModel> { ProductListViewModel() }
    viewModel<AppIntroduceViewModel> { AppIntroduceViewModel() }

    viewModel<DetailViewModel> { DetailViewModel() }
    viewModel<ProductIntroduceViewModel> { ProductIntroduceViewModel() }
    viewModel<ReviewViewModel> { ReviewViewModel() }

}