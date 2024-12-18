package vn.md18.fsquareapplication.utils

import androidx.annotation.Nullable
import androidx.lifecycle.MutableLiveData

object FirebaseEvent {
    val serviceEvent: MutableLiveData<Event<Pair<Constant.NotificationEvent, Any>>> by lazy { MutableLiveData() }
    val nameListDevicesUpgrade: MutableLiveData<Event<String>> by lazy { MutableLiveData() }
    val leavingHomeEvent: MutableLiveData<String> by lazy { MutableLiveData() }
    val firebaseMessageFirmwareEvent: MutableLiveData<FireBaseEventState> by lazy { MutableLiveData() }
}

object InternetEvent {
    val changeStateInternetEvent: MutableLiveData<Boolean> by lazy { MutableLiveData() }
}



/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

class EventPreventLiveData<T>(content: T?) {
    private val mContent: T
    private var hasBeenHandled = false

    @get:Nullable
    val contentIfNotHandled: T?
        get() = if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            mContent
        }

    fun hasBeenHandled(): Boolean {
        return hasBeenHandled
    }

    init {
        requireNotNull(content) { "null values in Event are not allowed." }
        mContent = content
    }
}
