package vn.md18.fsquareapplication.features.checkout.repository.hi

import io.reactivex.Flowable
import vn.md18.fsquareapplication.data.model.DataResponse
import vn.md18.fsquareapplication.data.network.model.response.bag.GetBagResponse
import vn.md18.fsquareapplication.data.network.model.response.PaginationResponse

interface CheckoutRepository {
    fun getBagList() : Flowable<DataResponse<List<GetBagResponse>, PaginationResponse>>
}