package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.request.AddBagRequest
import vn.md18.fsquareapplication.data.network.model.request.FavoriteRequest
import vn.md18.fsquareapplication.data.network.model.request.UpdateQuantityBagRequest
import vn.md18.fsquareapplication.data.network.model.response.bag.AddBagResponse
import vn.md18.fsquareapplication.data.network.model.response.favorite.CreateFavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.DeleteBagResponse
import vn.md18.fsquareapplication.data.network.model.response.favorite.DeleteFavoriteRespone
import vn.md18.fsquareapplication.data.network.model.response.favorite.FavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.UpdateQuantityBagResponse

interface MainRepository {

    fun getProductList(
        size: Int? = null,
        page: Int? = null,
        search: String? = null,
        brand: String? = null,
        category: String? = null
    ): Flowable<DataResponse<List<ProductResponse>, PaginationResponse>>


    fun getFavoriteList() : Flowable<DataResponse<List<FavoriteResponse>, PaginationResponse>>

    fun createFavorite( favoriteRequest: FavoriteRequest ) : Flowable<CreateFavoriteResponse>

    fun deleteFavorite( id: String ) : Flowable<DeleteFavoriteRespone>

    fun getBagList() : Flowable<DataResponse<List<GetBagResponse>, PaginationResponse>>

    fun updateQuantityBag(id: String, updateQuantityBagRequest: UpdateQuantityBagRequest) : Flowable<UpdateQuantityBagResponse>
    fun createBag( addBagRequest: AddBagRequest ) : Flowable<AddBagResponse>

    fun deleteBag( ) : Flowable<DeleteBagResponse>
}