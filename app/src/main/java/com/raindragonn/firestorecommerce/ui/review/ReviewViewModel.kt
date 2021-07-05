package com.raindragonn.firestorecommerce.ui.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import com.raindragonn.firestorecommerce.data.model.Product
import com.raindragonn.firestorecommerce.data.model.Review
import com.raindragonn.firestorecommerce.data.model.User
import com.raindragonn.firestorecommerce.data.repository.ReviewRepository
import com.raindragonn.firestorecommerce.data.repository.UserRepository
import com.raindragonn.firestorecommerce.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
    private val product: Product
) : BaseViewModel() {

    private val _reviewList = MutableLiveData<List<Review>>()
    val reviewList: LiveData<List<Review>>
        get() = _reviewList

    private val _eventAdd = MutableLiveData<Unit>()
    val eventAdd: LiveData<Unit>
        get() = _eventAdd

    private var lastVisible: DocumentSnapshot? = null

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        viewModelScope.launch {
            _user.value = userRepository.getUser() ?: kotlin.run {
                userRepository.saveUser(User())
                User()
            }
        }
    }

    fun getMoreList() = viewModelScope.launch {
        startLoading()

        val listSnapshot = reviewRepository.getLimitProductReviewSnapshot(
            product.id!!, lastVisible, 5
        ).also {
            if (it.size() >= 1) {
                lastVisible = it.documents[it.size() - 1]
            }
        }.map { it.toObject<Review>() }

        val oldList: MutableList<Review> =
            _reviewList.value?.toMutableList() ?: emptyList<Review>().toMutableList()

        // 중복제거
        oldList.removeAll(listSnapshot)
        oldList.addAll(listSnapshot)

        // 재정렬
        oldList.sortByDescending {
            it.createdAt
        }

        _reviewList.value = oldList
        stopLoading()
    }

    private fun addEvent() {
        _eventAdd.value = Unit
    }

    fun writeReView(content: String, star: Float) = viewModelScope.launch {
        startLoading()
        reviewRepository.addReview(
            Review(
                userId = _user.value?.id,
                productId = product.id,
                content = content,
                star = star
            )
        )
        lastVisible = null
        stopLoading()
        getLatestReview(true)
    }

    fun removeReview(review: Review) = viewModelScope.launch {
        startLoading()
        reviewRepository.removeReview(review)
        lastVisible = null
        stopLoading()

        getLatestReview()
    }

    private fun getLatestReview(isAdd: Boolean = false) = viewModelScope.launch {
        startLoading()
        val list = reviewRepository.getLatestProductReviewSnapshot(
            product.id!!, lastVisible
        )
        _reviewList.value = list
        stopLoading()
        if (isAdd) addEvent()
    }
}





