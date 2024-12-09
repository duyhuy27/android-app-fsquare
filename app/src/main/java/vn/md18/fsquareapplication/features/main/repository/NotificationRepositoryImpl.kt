package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.NotificationResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse
import vn.md18.fsquareapplication.data.network.retrofit.ApplicationService
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(private val applicationService: ApplicationService): NotificationRepository  {
    override fun getNotificationList(): Flowable<DataResponse<List<NotificationResponse>, PaginationResponse>> {
        return applicationService.getListNotification()
    }

    override fun deleteNotification(id: String): Flowable<Boolean> {
        return applicationService.deleteListNotification(id)
            .map { response ->
                response.status == "success"
            }
            .onErrorReturn {
                false
            }
    }
}