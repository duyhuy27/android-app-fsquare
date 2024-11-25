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
}