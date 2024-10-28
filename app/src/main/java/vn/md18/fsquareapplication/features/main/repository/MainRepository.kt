package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.request.FavoriteRequest
import vn.md18.fsquareapplication.data.network.model.request.UpdateQuantityBagRequest
import vn.md18.fsquareapplication.data.network.model.response.CreateFavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.DeleteFavoriteRespone
import vn.md18.fsquareapplication.data.network.model.response.FavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.data.network.model.response.UpdateQuantityBagResponse

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
}