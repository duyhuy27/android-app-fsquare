package vn.md18.fsquareapplication.features.main.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.core.base.BaseViewModel
import vn.md18.fsquareapplication.data.model.DataState
import vn.md18.fsquareapplication.data.network.model.request.FavoriteRequest
import vn.md18.fsquareapplication.data.network.model.response.HistorySearchResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.features.main.repository.MainRepository
import vn.md18.fsquareapplication.features.main.repository.SearchRepository
import vn.md18.fsquareapplication.utils.extensions.NetworkExtensions
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject constructor(
    private val searchRepository: SearchRepository,
    private val mainRepository: MainRepository,
    private val networkExtensions: NetworkExtensions
) : BaseViewModel() {

    private val _listKeywordSearch = MutableLiveData<List<HistorySearchResponse>>()
    val listKeywordSearch: LiveData<List<HistorySearchResponse>> = _listKeywordSearch

    private val _listProductResultSearch = MutableLiveData<List<ProductResponse>>()
    val listProductResultSearch: LiveData<List<ProductResponse>> = _listProductResultSearch

    private val _favoriteState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val favoriteState: LiveData<DataState<Boolean>> get() = _favoriteState

    override fun onDidBindViewModel() {}

    fun getHistoryKeyWordSearchByUserId() {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    searchRepository.getKeyWordSearch()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            _listKeywordSearch.value = response.data ?: emptyList()
                        }, {
                            FSLogger.e(it.message.toString())
                        })
                )
            }
        }
    }

    fun saveKeywordSearch(keyword: String) {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    searchRepository.saveKeyWordSearch(keyword)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribe({}, { throwable ->
                            FSLogger.e("Error saving keyword: ${throwable.message}")
                        })
                )
            }
        }
    }

    fun getProductListGridBySearch(keyword: String) {
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    searchRepository.searchProductV1(search = keyword, size = 20, page = 1)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ response ->
                            _listProductResultSearch.value = response.data ?: emptyList()
                        }, {
                            FSLogger.e(it.message.toString())
                        })
                )
            }
        }
    }

    fun createFavorite(productId: String, isAdding: Boolean) {
        val favoriteRequest = FavoriteRequest(shoes = productId)
        setLoading(true)
        networkExtensions.checkInternet { isConnect ->
            if (isConnect) {
                compositeDisposable.add(
                    mainRepository.createFavorite(favoriteRequest)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .toObservable()
                        .subscribe({ _ ->
                            setLoading(false)
                            _favoriteState.value = DataState.Success(isAdding)
                        }, { error ->
                            setLoading(false)
                            _favoriteState.value = DataState.Error(error)
                        })
                )
            } else {
                setLoading(false)
                setErrorStringId(R.string.no_internet_connection)
            }
        }
    }
}