package vn.md18.fsquareapplication.core.base

interface BaseBehavior {
    fun onBecomeVisible()
    fun onBecomeInvisible()
    fun onViewLoaded()
    fun addViewListener()
    fun addDataObserver()
    fun onKeyboardShowing(isShowing : Boolean)
    fun onLoading(isLoading: Boolean = false)
    fun onError(error: Any)
}