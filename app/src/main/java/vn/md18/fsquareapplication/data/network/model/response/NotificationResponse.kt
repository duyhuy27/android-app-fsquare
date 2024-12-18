package vn.md18.fsquareapplication.data.network.model.response

import com.google.gson.annotations.SerializedName

//{
//    "_id": "5f4e6c4f7b4f8c1b2c8c9b1f",
//    "order": "123456789",
//    "title": "Order Confirmation",
//    "content": "Your order has been confirmed and is being processed.",
//    "createdAt": "2024-12-01T14:12:47.149+00:00"
//}
data class NotificationResponse(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("order")
    val order: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createdAt")
    val createdAt: String
)
