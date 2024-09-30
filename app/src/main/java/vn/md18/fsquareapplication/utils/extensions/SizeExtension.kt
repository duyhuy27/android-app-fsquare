package vn.md18.fsquareapplication.utils.extensions


import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.net.Uri
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import vn.md18.fsquareapplication.utils.Constant

fun dpToPx(dp: Float): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

fun pxToDp(px: Float): Float {
    return px / (Resources.getSystem().displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun spToPx(sp: Float) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().displayMetrics
)

fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

fun setWidthView(viewParent: View, viewAssign: View, upperWidth: Int = 0) {
    val width = viewParent.layoutParams.width
    val layoutParams = viewAssign.layoutParams
    layoutParams.width = width + upperWidth
    viewAssign.layoutParams = layoutParams
}

fun getDeviceDensityString(): String? {
    when (Resources.getSystem().displayMetrics.densityDpi) {
        DisplayMetrics.DENSITY_LOW -> return Constant.SizeUtils.SIZE_LOW
        DisplayMetrics.DENSITY_MEDIUM -> return Constant.SizeUtils.SIZE_MEDIUM
        DisplayMetrics.DENSITY_TV, DisplayMetrics.DENSITY_HIGH -> return Constant.SizeUtils.SIZE_HDPI
        DisplayMetrics.DENSITY_260, DisplayMetrics.DENSITY_280, DisplayMetrics.DENSITY_300, DisplayMetrics.DENSITY_XHIGH -> return Constant.SizeUtils.SIZE_XHDPI
        DisplayMetrics.DENSITY_340, DisplayMetrics.DENSITY_360, DisplayMetrics.DENSITY_400, DisplayMetrics.DENSITY_420, DisplayMetrics.DENSITY_440, DisplayMetrics.DENSITY_XXHIGH -> return Constant.SizeUtils.SIZE_XXHDPI
        DisplayMetrics.DENSITY_560, DisplayMetrics.DENSITY_XXXHIGH -> return Constant.SizeUtils.SIZE_XXXHDPI
    }
    return null
}

/**
 * Set width view
 */
fun setWidthView(viewAssign: View, expectedValue: Int = 0) {
    val layoutParams = viewAssign.layoutParams
    layoutParams.width = expectedValue
    viewAssign.layoutParams = layoutParams
}

/**
 * Returns boundary of the physical screen including system decor elements (if any) like navigation
 * bar in pixels (px).
 */
fun getPhysicalScreen(context: Context): Rect {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getRealMetrics(displayMetrics)
    return Rect(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
}

@SuppressLint("InternalInsetResource")
fun getBottomNavigationBarHeight(): Int {
    val resourceId = Resources.getSystem().getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) {
        Resources.getSystem().getDimensionPixelSize(resourceId)
    } else 0
}