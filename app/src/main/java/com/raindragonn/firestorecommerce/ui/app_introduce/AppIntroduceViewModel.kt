package com.raindragonn.firestorecommerce.ui.app_introduce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raindragonn.firestorecommerce.data.repository.AppIntroduceRepository
import com.raindragonn.firestorecommerce.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AppIntroduceViewModel(
    private val repositoryApp: AppIntroduceRepository
) : BaseViewModel() {

    private val _introText = MutableLiveData<String>()
    val introText: LiveData<String>
        get() = _introText


    fun getIntroText() = viewModelScope.launch {
        startLoading()
        _introText.value = repositoryApp.getIntroduce()?.intro
        stopLoading()
    }
}