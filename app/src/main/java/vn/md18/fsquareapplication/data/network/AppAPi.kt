package vn.md18.fsquareapplication.data.network

object AppAPi {
    //Define API
    const val AUTH_SIGNUP = "/auth/customer/v1/registrations"

    const val AUTH_LOGIN = "/auth/customer/v1/authentications"

    const val AUTH_VERIFY_OTP = "/auth/customer/v1/verifications"

    const val PRODUCT_LIST = "/api/customer/v2/shoes"

    const val PRODUCT_LIST_V1 = "/api/customer/v1/shoes"

    const val FAVORITE_LIST = "/api/customer/v1/favorites"

    const val BAG_LIST = "/api/customer/v1/bags"

    const val PROVINCE_LIST = "/api/customer/v1/locations/provinces"

    const val DISTRICTS_LIST = "/api/customer/v1/locations/districts"

    const val WARDS_LIST = "/api/customer/v1/locations/wards"

    const val LOCATION_LIST = "api/customer/v1/customers/location"

    const val ORDER_LIST = "api/customer/v1/orders"

    const val PROFILE_DATA = "api/customer/v1/customers/profile"

    const val CATEGORIES_LIST = "api/customer/v2/categories"

    const val BRANDS_LIST = "api/customer/v2/brands"

    const val ORDER_FEE = "api/customer/v1/orders/fee"

    const val PRODUCT_DETAIL = "api/customer/v2/shoes/"

    const val CLASSIFICATION_SHOES = "api/customer/v2/classifications/shoes"

    const val CLASSIFICATION = "api/customer/v2/classifications"

    const val SIZE = "api/customer/v2/sizes/classification"

    const val PAYMENT = "api/customer/v1/payments"

    const val REVIEWS = "api/customer/v1/reviews"

    const val POPULAR = "api/customer/v2/statistical"

    const val HISTORY_SEARCH = "api/customer/v1/histories"

    const val SAVE_KEYWORD_SEARCH = "api/customer/v1/histories"

    const val GET_LIST_NOTIFICATION = "api/customer/v1/notifications"

    const val DELETE_LIST_NOTIFICATION = "api/customer/v1/notifications/"

    const val CHECK_PAYMENT_ORDER = "api/customer/v1/payments/detail"
}