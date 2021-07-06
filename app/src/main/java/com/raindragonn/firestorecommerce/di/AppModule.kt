package com.raindragonn.firestorecommerce.di

import android.app.Activity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.raindragonn.firestorecommerce.data.api.*
import com.raindragonn.firestorecommerce.data.model.Product
import com.raindragonn.firestorecommerce.data.preference.SharedPreferenceManager
import com.raindragonn.firestorecommerce.data.repository.*
import com.raindragonn.firestorecommerce.ui.app_introduce.AppIntroduceViewModel
import com.raindragonn.firestorecommerce.ui.product_introduce.ProductIntroduceViewModel
import com.raindragonn.firestorecommerce.ui.product_list.ProductListViewModel
import com.raindragonn.firestorecommerce.ui.review.ReviewViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //ViewModel
    viewModel<ProductListViewModel> { ProductListViewModel(get()) }
    viewModel<AppIntroduceViewModel> { AppIntroduceViewModel(get()) }

    viewModel<ProductIntroduceViewModel> { ProductIntroduceViewModel() }
    viewModel<ReviewViewModel> { (product: Product) -> ReviewViewModel(get(), get(), product) }

    // FireStore
    single<FirebaseFirestore> { Firebase.firestore }

    // API and Repository
    single<AppIntroduceApi> { AppIntroduceFireStoreApi(get()) }
    single<AppIntroduceRepository> { AppIntroduceRepositoryImpl(get()) }
    single<ProductApi> { ProductFireStoreApi(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<ReviewApi> { ReviewFireStoreApi(get()) }
    single<ReviewRepository> { ReviewRepositoryImpl(get()) }
    single<UserApi> { UserFireStoreApi(get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    // SharedPreferenceManager
    single<SharedPreferenceManager> {
        SharedPreferenceManager(
            androidContext().getSharedPreferences(
                "pref",
                Activity.MODE_PRIVATE
            )
        )
    }

}