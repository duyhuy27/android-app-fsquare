package vn.md18.fsquareapplication.data.network.retrofit

import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.AppAPi
import vn.md18.fsquareapplication.data.network.model.request.AddBagRequest
import vn.md18.fsquareapplication.data.network.model.request.CheckPaymentRequest
import vn.md18.fsquareapplication.data.network.model.request.FavoriteRequest
import vn.md18.fsquareapplication.data.network.model.request.GetPaymentDetailRequest

import vn.md18.fsquareapplication.data.network.model.request.LoginRequest
import vn.md18.fsquareapplication.data.network.model.request.PostPaymentRequest
import vn.md18.fsquareapplication.data.network.model.request.PostReviewRequest
import vn.md18.fsquareapplication.data.network.model.request.SignUpRequest
import vn.md18.fsquareapplication.data.network.model.request.UpdateProfileRequest
import vn.md18.fsquareapplication.data.network.model.request.UpdateQuantityBagRequest
import vn.md18.fsquareapplication.data.network.model.request.VerifyRequest
import vn.md18.fsquareapplication.data.network.model.request.location.AddLocationCustomerRequest
import vn.md18.fsquareapplication.data.network.model.request.location.UpdateLocationCustomerRequest
import vn.md18.fsquareapplication.data.network.model.request.location.UpdateOrderRequest
import vn.md18.fsquareapplication.data.network.model.request.order.AddOrderRequest
import vn.md18.fsquareapplication.data.network.model.request.order.OrderFeeRequest
import vn.md18.fsquareapplication.data.network.model.response.BrandResponse
import vn.md18.fsquareapplication.data.network.model.response.CategoriesResponse
import vn.md18.fsquareapplication.data.network.model.response.Classification
import vn.md18.fsquareapplication.data.network.model.response.ClassificationShoes
import vn.md18.fsquareapplication.data.network.model.response.GetClassificationResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.AddBagResponse
import vn.md18.fsquareapplication.data.network.model.response.favorite.CreateFavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.DeleteBagResponse
import vn.md18.fsquareapplication.data.network.model.response.favorite.DeleteFavoriteRespone
import vn.md18.fsquareapplication.data.network.model.response.favorite.FavoriteResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.GetDistrictsResponse
import vn.md18.fsquareapplication.data.network.model.response.GetOrderRespose
import vn.md18.fsquareapplication.data.network.model.response.GetPaymentDetailResponse
import vn.md18.fsquareapplication.data.network.model.response.GetPaymentResponse

import vn.md18.fsquareapplication.data.network.model.response.GetProvinceResponse
import vn.md18.fsquareapplication.data.network.model.response.GetReviewResponse
import vn.md18.fsquareapplication.data.network.model.response.GetWardsRepose
import vn.md18.fsquareapplication.data.network.model.response.HistorySearchResponse
import vn.md18.fsquareapplication.data.network.model.response.NotificationResponse
import vn.md18.fsquareapplication.data.network.model.response.auth.LoginResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.model.response.PostPaymentResponse
import vn.md18.fsquareapplication.data.network.model.response.PostReviewResponse
import vn.md18.fsquareapplication.data.network.model.response.ProductResponse
import vn.md18.fsquareapplication.data.network.model.response.ReviewResponse
import vn.md18.fsquareapplication.data.network.model.response.UpdateProfileResponse
import vn.md18.fsquareapplication.data.network.model.response.auth.SignUpResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.UpdateQuantityBagResponse
import vn.md18.fsquareapplication.data.network.model.response.auth.VerifyResponse
import vn.md18.fsquareapplication.data.network.model.response.location.AddLocationCustomerResponse
import vn.md18.fsquareapplication.data.network.model.response.location.DeleteLocationCustomerResponse
import vn.md18.fsquareapplication.data.network.model.response.location.GetLocationCustomerResponse
import vn.md18.fsquareapplication.data.network.model.response.location.UpdateLocationCustomerResponse
import vn.md18.fsquareapplication.data.network.model.response.order.AddOrderResponse
import vn.md18.fsquareapplication.data.network.model.response.order.DeleteOrderResponse
import vn.md18.fsquareapplication.data.network.model.response.order.GetOrderDetailResponse
import vn.md18.fsquareapplication.data.network.model.response.order.OrderFeeResponse
import vn.md18.fsquareapplication.data.network.model.response.order.UpdateOrderResponse
import vn.md18.fsquareapplication.data.network.model.response.profile.GetProfileResponse

interface ApplicationService {

    // auth
    @POST(AppAPi.AUTH_SIGNUP)
    fun signUp(
        @Body signUpRequest: SignUpRequest
    ) : Flowable<SignUpResponse>


    @POST(AppAPi.AUTH_LOGIN)
    fun login(
        @Body loginRequest: LoginRequest
    ) : Flowable<LoginResponse>

    @POST(AppAPi.AUTH_VERIFY_OTP)
    fun verify(
        @Body verifyRequest: VerifyRequest
    ) : Flowable<VerifyResponse>


    @GET(AppAPi.PRODUCT_LIST)
    fun getProduct(
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Query("search") search: String? = null,
        @Query("brand") brand: String? = null,
        @Query("category") category: String? = null
    ) : Flowable<DataResponse<List<ProductResponse>, PaginationResponse>>

    @GET(AppAPi.POPULAR)
    fun getProductPopular(
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Query("search") search: String? = null,
        @Query("brand") brand: String? = null,
        @Query("category") category: String? = null
    ) : Flowable<DataResponse<List<ProductResponse>, PaginationResponse>>

    @GET(AppAPi.PRODUCT_LIST_V1)
    fun getProductV1(
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Query("search") search: String? = null,
        @Query("brand") brand: String? = null,
        @Query("category") category: String? = null
    ) : Flowable<DataResponse<List<ProductResponse>, PaginationResponse>>

    @GET(AppAPi.FAVORITE_LIST)
    fun getFavorite() : Flowable<DataResponse<List<FavoriteResponse>, PaginationResponse>>

    @POST(AppAPi.FAVORITE_LIST)
    fun createFavorite(
        @Body favoriteRequest: FavoriteRequest
    ) : Flowable<CreateFavoriteResponse>

    @DELETE(AppAPi.FAVORITE_LIST + "/{id}")
    fun deleteFavorite(
        @Path("id") id: String
    ) : Flowable<DeleteFavoriteRespone>

    @GET(AppAPi.BAG_LIST)
    fun getBags() : Flowable<DataResponse<List<GetBagResponse>, PaginationResponse>>

    @POST(AppAPi.BAG_LIST)
    fun createBag(
        @Body addBagRequest: AddBagRequest
    ) : Flowable<AddBagResponse>

    @PATCH(AppAPi.BAG_LIST + "/{id}")
    fun updateQuantityBag(
        @Path("id") id: String,
        @Body updateQuantityBagRequest: UpdateQuantityBagRequest
    ) : Flowable<UpdateQuantityBagResponse>

    @DELETE(AppAPi.BAG_LIST)
    fun deleteBag(
    ) : Flowable<DeleteBagResponse>

    @DELETE(AppAPi.BAG_LIST + "/{id}")
    fun deleteBagById(
        @Path("id") id: String,
    ) : Flowable<DeleteBagResponse>

    @GET(AppAPi.PROVINCE_LIST)
    fun getProvinces() : Flowable<DataResponse<List<GetProvinceResponse>, PaginationResponse>>

    @GET(AppAPi.DISTRICTS_LIST + "/{id}")
    fun getDistrict(
        @Path("id") id: String,
    ) : Flowable<DataResponse<List<GetDistrictsResponse>, PaginationResponse>>

    @GET(AppAPi.WARDS_LIST + "/{id}")
    fun getWard(
        @Path("id") id: String,
    ) : Flowable<DataResponse<List<GetWardsRepose>, PaginationResponse>>

    @GET(AppAPi.LOCATION_LIST)
    fun getLocationCustomer() : Flowable<DataResponse<List<GetLocationCustomerResponse>, PaginationResponse>>

    @POST(AppAPi.LOCATION_LIST)
    fun createLocationCustomer(
        @Body locationCustomerRequest: AddLocationCustomerRequest
    ) : Flowable<AddLocationCustomerResponse>

    @PATCH(AppAPi.LOCATION_LIST + "/{id}")
    fun updateLocationCustomer(
        @Path("id") id: String,
        @Body locationCustomerRequest: UpdateLocationCustomerRequest
    ) : Flowable<UpdateLocationCustomerResponse>

    @DELETE(AppAPi.LOCATION_LIST + "/{id}")
    fun deleteLocationCustomer(
        @Path("id") id: String
    ) : Flowable<DeleteLocationCustomerResponse>

    @GET(AppAPi.ORDER_LIST)
    fun getOrder(
        @Query("status") status: String
    ) : Flowable<DataResponse<List<GetOrderRespose>, PaginationResponse>>

    @GET(AppAPi.ORDER_LIST + "/{id}")
    fun getOrderById(
        @Path("id") id: String,
    ) : Flowable<DataResponse<GetOrderDetailResponse, PaginationResponse>>

    @POST(AppAPi.ORDER_LIST)
    fun createOrder(
        @Body addOrderRequest: AddOrderRequest
    ) : Flowable<AddOrderResponse>

    @PATCH(AppAPi.ORDER_LIST + "/{id}")
    fun updateOrderStatus(
        @Path("id") id: String,
        @Body updateOrderRequest: UpdateOrderRequest
    ) : Flowable<UpdateOrderResponse>

    @DELETE(AppAPi.ORDER_LIST + "/{id}")
    fun deleteOrder(
        @Path("id") id: String
    ) : Flowable<DeleteOrderResponse>

    @GET(AppAPi.PROFILE_DATA)
    fun getProfile(
    ) : Flowable<DataResponse<GetProfileResponse, PaginationResponse>>

    @PATCH(AppAPi.PROFILE_DATA)
    fun updateProfile(
        @Body updateProfileRequest: UpdateProfileRequest
    ) : Flowable<UpdateProfileResponse>

    @GET(AppAPi.PRODUCT_DETAIL + "{id}")
    fun getProductDetail(
        @Path("id") id: String
    ) : Flowable<DataResponse<ProductResponse, PaginationResponse>>

    @GET(AppAPi.PRODUCT_LIST_V1 + "/{id}")
    fun getProductDetailV1(
        @Path("id") id: String
    ) : Flowable<DataResponse<ProductResponse, PaginationResponse>>

    @GET(AppAPi.CATEGORIES_LIST)
    fun getCategories(
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Query("search") search: String? = null,
    ) : Flowable<DataResponse<List<CategoriesResponse>, PaginationResponse>>

    @GET(AppAPi.BRANDS_LIST)
    fun getBrands(
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Query("search") search: String? = null,
    ) : Flowable<DataResponse<List<BrandResponse>, PaginationResponse>>

    @POST(AppAPi.ORDER_FEE)
    fun getFeeOrder(
        @Body orderFeeRequest: OrderFeeRequest
    ) : Flowable<OrderFeeResponse>

    @GET(AppAPi.CLASSIFICATION_SHOES + "/{id}")
    fun getColor(
        @Path("id") id: String
    ) : Flowable<DataResponse<List<GetClassificationResponse>, PaginationResponse>>

    @GET(AppAPi.CLASSIFICATION + "/{id}")
    fun getClassification(
        @Path("id") id: String
    ) : Flowable<DataResponse<Classification, PaginationResponse>>

    @GET(AppAPi.SIZE + "/{id}")
    fun getSize(
        @Path("id") id: String
    ) : Flowable<DataResponse<List<ClassificationShoes>, PaginationResponse>>

    @POST(AppAPi.PAYMENT)
    fun addPayment(
        @Body postPaymentRequest: PostPaymentRequest
    ) : Flowable<DataResponse<PostPaymentResponse, PaginationResponse>>

    @GET(AppAPi.PAYMENT)
    fun getPayments(

    ) : Flowable<DataResponse<List<GetPaymentResponse>, PaginationResponse>>

    @POST(AppAPi.PAYMENT)
    fun getPaymentDetail(
        @Body getPaymentDetailRequest: GetPaymentDetailRequest
    ) : Flowable<DataResponse<GetPaymentDetailResponse, PaginationResponse>>

    @POST(AppAPi.REVIEWS)
    fun postReview(
        @Body postReviewRequest: PostReviewRequest
    ) : Flowable<PostReviewResponse>

    @GET(AppAPi.REVIEWS + "/shoes/{id}")
    fun getReview(
        @Path("_id") id: String
    ) : Flowable<DataResponse<List<ReviewResponse>, PaginationResponse>>

    @GET(AppAPi.LIST_REVIEWS + "/{id}")
    fun getReviews(
        @Path("id") id: String,
    ) : Flowable<DataResponse<List<GetReviewResponse>, PaginationResponse>>

    @GET(AppAPi.HISTORY_SEARCH)
    fun getHistorySearch(
    ) : Flowable<DataResponse<List<HistorySearchResponse>, PaginationResponse>>

    @POST(AppAPi.SAVE_KEYWORD_SEARCH)
    fun saveKeyWordSearch(
        @Body keyword: String
    ) : Flowable<DataResponse<HistorySearchResponse, PaginationResponse>>

    @GET(AppAPi.GET_LIST_NOTIFICATION)
    fun getListNotification(
    ) : Flowable<DataResponse<List<NotificationResponse>, PaginationResponse>>

    @DELETE(AppAPi.DELETE_LIST_NOTIFICATION + "/{id}")
    fun deleteListNotification(
        @Path("id") id: String
    ) : Flowable<DataResponse<NotificationResponse, PaginationResponse>>

    @POST(AppAPi.CHECK_PAYMENT_ORDER)
    fun checkPaymentOrder(
        @Body checkPaymentRequest: CheckPaymentRequest
    ) : Flowable<DataResponse<PostPaymentResponse, PaginationResponse>>
}