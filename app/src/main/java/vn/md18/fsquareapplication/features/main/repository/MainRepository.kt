package vn.md18.fsquareapplication.features.main.repository

import io.reactivex.Single

interface MainRepository {

    fun getProductList() : Single<Boolean>
}