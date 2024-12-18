package vn.md18.fsquareapplication.utils.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.checkedChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.databinding.ItemToastDefaultBinding
import vn.md18.fsquareapplication.utils.Constant
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

inline fun SwitchCompat.checkChangeWithRx(crossinline onChangeState: (Boolean) -> Unit) {
    this.checkedChanges()
        .debounce(300, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread()).subscribe {
            onChangeState.invoke(it)
        }
}

fun ImageView.loadImageDrawable(idDrawable: Int) {
    Glide.with(this.context).load(idDrawable).fitCenter().into(this)
}

@JvmOverloads
inline fun <T : ViewBinding> ViewGroup.viewBinding(
    crossinline bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> T,
    attachToParent: Boolean = false,
) = bindingInflater.invoke(LayoutInflater.from(this.context), this, attachToParent)

fun NavController.navigateWithAnimation(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null
) {
    val mNavOptions = navOptions ?: NavOptions.Builder()
        .setEnterAnim(R.anim.slide_in_right)
        .setExitAnim(R.anim.slide_out_left)
        .setPopEnterAnim(R.anim.slide_in_left)
        .setPopExitAnim(R.anim.slide_out_right)
        .build()

    navigate(resId, args, mNavOptions)
}

fun delayFunction(timeDelay: Long, function: () -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        delay(timeMillis = timeDelay)
        function.invoke()
    }
}

fun Context.showCustomToast(
    content: String,
    style: Constant.ToastStatus? = null,
    durationToast: Int = Toast.LENGTH_LONG,
    autoCancelOnPause: Boolean? = true,
) {
    val binding = ItemToastDefaultBinding.inflate(LayoutInflater.from(this)).apply {
        tvDefault.text = content
        val imageResource = when (style) {
            Constant.ToastStatus.SUCCESS -> R.drawable.ic_noti_success_24
            Constant.ToastStatus.FAILURE -> R.drawable.ic_noti_failure_24
            Constant.ToastStatus.WARNING -> R.drawable.ic_warning
            Constant.ToastStatus.INFO -> R.drawable.ic_info
            else -> null
        }
        imageResource?.let {
            ivToast.setImageResource(it)
        } ?: ivToast.hide()
    }

    val toast = Toast(this).apply {
        view = binding.root
        duration = durationToast
        setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
    }
    toast.show()

    if (autoCancelOnPause == true) {
        val lifecycleOwner = this as? LifecycleOwner
        lifecycleOwner?.let { owner ->
            owner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onPause(owner: LifecycleOwner) {
                    toast.cancel()
                    owner.lifecycle.removeObserver(this)
                }
            })
        }
    }

}

fun Context.showCustomToastShort(content: String, style: Constant.ToastStatus? = null) {
    showCustomToast(content, style, Toast.LENGTH_SHORT)
}

fun Context.showToast(content: String) {
    showCustomToast(content, durationToast = Toast.LENGTH_LONG)
}

fun Context.showToastShort(content: String) {
    showCustomToast(content, durationToast = Toast.LENGTH_SHORT)
}

fun Context.showToastWithDuration(content: String, durationToast: Int) {
    Toast(this).apply {
        duration = durationToast
        setText(content)
        show()
    }
}

/**
 * ImageView Loading Image By path
 */
fun ImageView.loadImageDrawableNoCache(drawable: Drawable?) {
    drawable?.let {
        Glide.with(this.context)
            .load(drawable)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(this)
    }
}

/**
 * ImageView Loading Image By Uri
 */
fun ImageView.loadImageUri(uri: Uri?) {
    Glide.with(this.context).load(uri).fitCenter().centerCrop().into(this)
}

/**
 * ImageView Loading Image By URL
 */
//fun ImageView.loadImageURL(url: String?, drawable: Int? = null) {
//    Glide.with(this.context).clear(this)
//
//    if (url.isNullOrEmpty() || url == "null") {
//        this.setImageResource(drawable ?: R.drawable.shape_bg_grey_radius_13dp)
//        return
//    }
//
//    val apiLink = CryptoAES.decrypt3DES(BuildConfig.PHP_SERVER)
//    val requestOptions = RequestOptions()
//        .diskCacheStrategy(DiskCacheStrategy.ALL)
//        .placeholder(drawable ?: R.drawable.shape_bg_grey_radius_13dp)
//        .error(drawable ?: R.drawable.shape_bg_grey_radius_13dp)
//        .transform(CenterCrop())
//
//    Glide.with(this.context)
//        .load("$apiLink/$url")
//        .thumbnail(0.25f)
//        .apply(requestOptions)
//        .into(this)
//}
//
//
///**
// * ImageView Loading Image By URL and diskCacheStrategy
// */
fun ImageView.loadImageUrlDiskCacheStrategy(url: String?, drawable: Int? = null) {
    if (url.isNullOrEmpty() or (url == "null")) {
        this.setImageResource(drawable ?: R.drawable.shape_bg_grey_radius_13dp)
        return
    }
    if (drawable != null) {
        Glide.with(this.context).load(url).placeholder(drawable)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(this)
    } else {
        Glide.with(this.context).load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(this)
    }
}
//
///**
// * ImageView Loading Image By URL
// */
//fun ImageView.loadImageURLWithError(url: String?, drawable: Int? = null) {
//    if (url.isNullOrEmpty() or (url == "null")) {
//        this.setImageResource(drawable ?: R.drawable.shape_bg_grey_radius_13dp)
//        return
//    }
//    val apiLink = CryptoAES.decrypt3DES(BuildConfig.PHP_SERVER)
//
//    Glide.with(this.context).load("${apiLink}/$url").error(drawable).into(this)
//}
fun ImageView.loadImageURL(url: String?, drawable: Int? = null) {
    Glide.with(this.context).clear(this)

    if (url.isNullOrEmpty() || url == "null") {
        this.setImageResource(drawable ?: R.drawable.shape_bg_grey_radius_13dp)
        return
    }

    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(drawable ?: R.drawable.shape_bg_grey_radius_13dp)
        .error(drawable ?: R.drawable.shape_bg_grey_radius_13dp)
        .transform(CenterCrop())

    Glide.with(this.context)
        .load("$url")
        .thumbnail(0.25f)
        .apply(requestOptions)
        .into(this)
}