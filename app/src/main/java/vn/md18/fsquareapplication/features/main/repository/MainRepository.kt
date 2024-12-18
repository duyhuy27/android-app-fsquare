package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.request.AddBagRequest
import vn.md18.fsquareapplication.data.network.model.request.FavoriteRequest
import vn.md18.fsquareapplication.data.network.model.request.GetPaymentDetailRequest
import vn.md18.fsquareapplication.data.network.model.request.PostPaymentRequest
import vn.md18.fsquareapplication.data.network.model.request.UpdateQuantityBagRequest
import vn.md18.fsquareapplication.data.network.model.request.order.OrderFeeRequest
import vn.md18.fsquareapplication.data.network.model.response.BrandResponse
import vn.md18.fsquareapplication.data.network.model.response.CategoriesResponse
import vn.md18.fsquareapplication.data.network.model.response.GetPaymentDetailResponse
import vn.md18.fsquareapplication.data.network.model.response.GetPaymentResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.AddBagResponse
import vn.md18.fsquareapplication.data.network.model.response.favorite.CreateFavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.DeleteBagResponse
import vn.md18.fsquareapplication.data.network.model.response.favorite.DeleteFavoriteRespone
import vn.md18.fsquareapplication.data.network.model.response.favorite.FavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.PostPaymentResponse
import vn.md18.fsquareapplication.data.network.model.response.PostReviewResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.data.network.model.response.UpdateProfileResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.UpdateQuantityBagResponse
import vn.md18.fsquareapplication.data.network.model.response.order.OrderFeeResponse

interface MainRepository {

    fun getProductList(
        size: Int? = null,
        page: Int? = null,
        search: String? = null,
        brand: String? = null,
        category: String? = null
    ): Flowable<DataResponse<List<ProductResponse>, PaginationResponse>>

    fun getProductListPopular(
        size: Int? = null,
        page: Int? = null,
        search: String? = null,
        brand: String? = null,
        category: String? = null
    ): Flowable<DataResponse<List<ProductResponse>, PaginationResponse>>

    fun getProductListByBrandV1(
        size: Int? = null,
        page: Int? = null,
        search: String? = null,
        brand: String? = null,
        category: String? = null
    ): Flowable<DataResponse<List<ProductResponse>, PaginationResponse>>

    fun getProductListV1(
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

    fun deleteBagById( id: String ) : Flowable<DeleteBagResponse>

    fun deleteBag( ) : Flowable<DeleteBagResponse>

    fun getCategories(
        size: Int? = null,
        page: Int? = null,
        search: String? = null,
    ) : Flowable<DataResponse<List<CategoriesResponse>, PaginationResponse>>

    fun getBrands(
        size: Int? = null,
        page: Int? = null,
        search: String? = null,
    ) : Flowable<DataResponse<List<BrandResponse>, PaginationResponse>>

    fun getOrderFee(
        orderFeeRequest: OrderFeeRequest
    ) : Flowable<OrderFeeResponse>

    fun getPayments(
    ) : Flowable<DataResponse<List<GetPaymentResponse>, PaginationResponse>>

    fun postPayments(
        postPaymentRequest: PostPaymentRequest
    ) : Flowable<DataResponse<PostPaymentResponse, PaginationResponse>>

    fun getDetailPayment(
        getPaymentDetailRequest: GetPaymentDetailRequest
    ) : Flowable<DataResponse<GetPaymentDetailResponse, PaginationResponse>>

    fun sendTokenToBackend(token: String): Flowable<UpdateProfileResponse>

}