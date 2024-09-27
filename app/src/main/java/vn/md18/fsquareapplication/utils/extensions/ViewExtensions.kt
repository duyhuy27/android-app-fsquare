package vn.md18.fsquareapplication.utils.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.SystemClock
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import vn.md18.fsquareapplication.utils.extensions.ViewUtils.lastClick
import java.util.concurrent.TimeUnit

fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}

/**
 * Get colorCompat
 * @param color : Int
 * @return color :Int
 */
fun Context.getColorCompat(color: Int): Int {
    return ContextCompat.getColor(this, color)
}

fun Context.getColorStateListCompat(color: Int): ColorStateList? {
    return ContextCompat.getColorStateList(this, color)
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.setEnableView(isEnable: Boolean) {
    alpha = if (!isEnable) 0.3F else 1.0F
    isClickable = isEnable
    this.isEnabled = isEnable
}

fun View.setEnableView(alphaValue: Float, isEnable: Boolean) {
    isEnabled = isEnable
    alpha = if (!isEnable) alphaValue else 1.0F
    isClickable = isEnable
}

/**
 * Set focus view ->
 * @param isEnable = true -> isClickable , isFocusable
 *        isEnable = false -> unClickable , unFocusable
 */
fun View.setFocusableView(isEnable: Boolean) {
    isClickable = isEnable
    isFocusable = isEnable
}

fun View.setOnSafeClickListener(
    duration: Long = 1000,
    onClick: () -> Unit
) {
    setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClick >= duration) {
            onClick()
            lastClick = SystemClock.elapsedRealtime()
        }
    }
}

object ViewUtils {
    var lastClick = 0L
}

fun AppCompatTextView.setColorTextView(color: Int) {
    return (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.setTextColor(context.getColor(color))
    } else {
        this.setTextColor(context.getColorCompat(color))
    })
}

inline fun View.safeClickWithRx(crossinline onClickView: () -> Unit) {
    this.clicks()
        .debounce(300, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            onClickView.invoke()
        }

}