package vn.md18.fsquareapplication.utils.rx

import io.reactivex.observers.DisposableObserver
import timber.log.Timber
import vn.md18.fsquareapplication.data.model.ErrorResponse
import vn.md18.fsquareapplication.utils.ErrorUtils
import kotlin.reflect.KFunction1


abstract class ObservableWrapper<Data>(
    private val function: KFunction1<Boolean, Unit>?,
    private val functionError: KFunction1<ErrorResponse, Unit>
) : DisposableObserver<Data>() {

    abstract fun onSuccessData(data: Data)

    override fun onNext(data: Data & Any) {
        onSuccessData(data)
    }

    override fun onComplete() {
        function?.invoke(false)
    }

    override fun onError(error: Throwable) {
        ErrorUtils.getError(error)?.let {
            Timber.i("errorResponse: $it")
            functionError.invoke(it)
        }
    }

    override fun onStart() {
        function?.invoke(true)
    }
}