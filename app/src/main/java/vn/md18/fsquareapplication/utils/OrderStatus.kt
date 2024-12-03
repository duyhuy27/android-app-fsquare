package vn.md18.fsquareapplication.utils

import java.io.Serializable

enum class OrderStatus(val status: String) {
    PENDING("pending"),
    PROCESSING("processing"),
    SHIPPED("shipped"),
    DELIVERED("delivered"),
    CONFIRMED("confirmed"),
    CANCELED("cancelled"),
    RETURNED("returned");

    companion object {
        fun fromString(value: String): OrderStatus {
            return values().first { it.status == value }
        }
    }
}



