package vn.md18.fsquareapplication.features.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.request.FavoriteRequest
import vn.md18.fsquareapplication.data.network.model.request.PostReviewRequest
import vn.md18.fsquareapplication.data.network.model.response.PostReviewResponse
import vn.md18.fsquareapplication.data.network.model.response.order.AddOrderResponse
import vn.md18.fsquareapplication.features.main.repository.OrderRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReviewViewmodel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val networkExtensions: NetworkExtensions
) : BaseViewModel() {

    private val _addReviewState: MutableLiveData<DataState<PostReviewResponse>> = MutableLiveData()
    val addReviewState: LiveData<DataState<PostReviewResponse>> get() = _addReviewState

    init {
        FSLogger.d("ReviewViewModel", "ReviewViewmodel created")
    }
    override fun onDidBindViewModel() {

    }

    fun toast(){
        FSLogger.d("phuc", "hihi")
    }

    fun createReviews(files: List<File>,order: String, rating: Int, content: String) {
        val postReviewRequest = PostReviewRequest(files, order, rating, content)
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    orderRepository.postReviews(postReviewRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            setLoading(false)
                            _addReviewState.value = DataState.Success(response)
                        }, { error ->
                            setLoading(false)
                            _addReviewState.value = DataState.Error(error)
                        })
                )
            } else {
                setLoading(false)
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }
}