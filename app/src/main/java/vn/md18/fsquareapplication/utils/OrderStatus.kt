package vn.md18.fsquareapplication.utils

import java.io.Serializable

enum class OrderStatus(val status: String) {
    PENDING("Pending"),
    PROCESSING("Processing"),
    SHIPPED("Shipped"),
    DELIVERED("Delivered"),
    CANCELED("Canceled");

    companion object {
        fun fromString(value: String): OrderStatus {
            return values().first { it.status == value }
        }
    }
}



