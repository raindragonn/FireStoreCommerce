package com.raindragonn.firestorecommerce

import android.app.Application
import com.raindragonn.firestorecommerce.data.sample.SampleRepository
import com.raindragonn.firestorecommerce.data.sample.response.toProduct
import com.raindragonn.firestorecommerce.di.appModule
import com.raindragonn.firestorecommerce.util.DataGenerator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CommerceApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            if (BuildConfig.DEBUG) {
                printLogger(Level.DEBUG)
            }

            androidContext(this@CommerceApplication)

            modules(appModule)
        }

//        샘플데이터(네이버 검색API - 도서 검색 "안드로이드" 결과 100개)
        GlobalScope.launch {
//            DataGenerator.generate(SampleRepository.getSampleData("안드로이드").map {
//                it.toProduct()
//            })
//            DataGenerator.reViewGenerate("F4gu9conKFAlLvkx8m2t","1AeNm9gn2Gr1rtSAWX7H")
//            DataGenerator.reViewGenerate("F4gu9conKFAlLvkx8m2t","2KcFPa6raOdXB8ZHmsn5")
//            DataGenerator.reViewGenerate("F4gu9conKFAlLvkx8m2t","37APiMh5k39W3BwfhlKg")
        }
    }
}