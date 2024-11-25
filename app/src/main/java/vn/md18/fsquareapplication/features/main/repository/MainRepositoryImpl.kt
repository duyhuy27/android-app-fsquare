package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.request.AddBagRequest
import vn.md18.fsquareapplication.data.network.model.request.FavoriteRequest
import vn.md18.fsquareapplication.data.network.model.request.UpdateQuantityBagRequest
import vn.md18.fsquareapplication.data.network.model.request.order.OrderFeeRequest
import vn.md18.fsquareapplication.data.network.model.response.BrandResponse
import vn.md18.fsquareapplication.data.network.model.response.CategoriesResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.AddBagResponse
import vn.md18.fsquareapplication.data.network.model.response.favorite.CreateFavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.DeleteBagResponse
import vn.md18.fsquareapplication.data.network.model.response.favorite.DeleteFavoriteRespone
import vn.md18.fsquareapplication.data.network.model.response.favorite.FavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.UpdateQuantityBagResponse
import vn.md18.fsquareapplication.data.network.model.response.order.OrderFeeResponse
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import vn.md18.fsquareapplication.di.component.datamanager.DataManager
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val applicationServices: ApplicationService,
    private val dataManager: DataManager,
) : MainRepository {

    override fun getProductList(
        size: Int?,
        page: Int?,
        search: String?,
        brand: String?,
        category: String?
    ): Flowable<DataResponse<List<ProductResponse>, PaginationResponse>> {
        return applicationServices.getProduct(size = size!!, page = page!!)
    }

    override fun getProductListV1(
        size: Int?,
        page: Int?,
        search: String?,
        brand: String?,
        category: String?
    ): Flowable<DataResponse<List<ProductResponse>, PaginationResponse>> {
        return applicationServices.getProductV1(size = size!!, page = page!!)
    }

    override fun getFavoriteList(): Flowable<DataResponse<List<FavoriteResponse>, PaginationResponse>> {
        return applicationServices.getFavorite()
    }

    override fun createFavorite(favoriteRequest: FavoriteRequest): Flowable<CreateFavoriteResponse> {
        return applicationServices.createFavorite(favoriteRequest)
    }

    override fun deleteFavorite(id: String): Flowable<DeleteFavoriteRespone> {
        return applicationServices.deleteFavorite(id)
    }

    override fun getBagList(): Flowable<DataResponse<List<GetBagResponse>, PaginationResponse>> {
        return applicationServices.getBags()
    }

    override fun updateQuantityBag(
        id: String,
        updateQuantityBagRequest: UpdateQuantityBagRequest
    ): Flowable<UpdateQuantityBagResponse> {
        return applicationServices.updateQuantityBag(id = id, updateQuantityBagRequest)
    }

    override fun createBag(addBagRequest: AddBagRequest): Flowable<AddBagResponse> {
        return applicationServices.createBag(addBagRequest)
    }

    override fun deleteBagById(id: String): Flowable<DeleteBagResponse> {
        return applicationServices.deleteBagById(id)
    }

    override fun deleteBag(): Flowable<DeleteBagResponse> {
        return applicationServices.deleteBag()
    }

    override fun getCategories(
        size: Int?,
        page: Int?,
        search: String?
    ): Flowable<DataResponse<List<CategoriesResponse>, PaginationResponse>> {
        return applicationServices.getCategories(size = size!!, page = page!!)
    }

    override fun getBrands(
        size: Int?,
        page: Int?,
        search: String?
    ): Flowable<DataResponse<List<BrandResponse>, PaginationResponse>> {
        return applicationServices.getBrands(size = size!!, page = page!!)
    }

    override fun getOrderFee(orderFeeRequest: OrderFeeRequest): Flowable<OrderFeeResponse> {
        return applicationServices.getFeeOrder(orderFeeRequest)
    }
}