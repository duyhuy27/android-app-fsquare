package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.NotificationResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse

interface NotificationRepository {
    fun getNotificationList() : Flowable<DataResponse<List<NotificationResponse>, PaginationResponse>>

    fun deleteNotification(id: String) : Flowable<Boolean>
}