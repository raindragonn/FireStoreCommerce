package com.raindragonn.firestorecommerce

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.raindragonn.firestorecommerce.data.model.Product
import com.raindragonn.firestorecommerce.databinding.ActivityMainBinding
import com.raindragonn.firestorecommerce.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val navigationController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainNavigationHostContainer) as NavHostFragment)
            .navController
    }

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        // Top 레벨 선언, 선언된 프래그먼트의 경우 툴바에 뒤로가기 버튼을 만들지 않는다.(스택을 쌓지않음)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.mainFragment))
        binding.toolbar.setupWithNavController(navigationController, appBarConfiguration)

        navigationController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.detailFragment) {
                val product = arguments?.getParcelable<Product>("product")
                product ?: return@addOnDestinationChangedListener
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    binding.toolbar.title =
                        Html.fromHtml(product.title, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    binding.toolbar.title = Html.fromHtml(product.title)
                }
            }
        }
    }
}