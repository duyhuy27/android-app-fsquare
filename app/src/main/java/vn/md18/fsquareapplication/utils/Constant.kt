package vn.md18.fsquareapplication.utils

object Constant {
    const val KEY_UID = "KEY_UID"
    const val KEY_USER_NAME = "KEY_USER_NAME"
    const val KEY_INTRO = "KEY_INTRO"
    const val KEY_TOKEN = "KEY_TOKEN"
    const val EMPTY_STRING = ""

    const val REQUEST_CODE_WRITE_EXTERNAL_PERMISSION = 11
    const val REQUEST_CODE_ACCESS_FINE_LOCATION_AP_MODE = 15
    const val REQUEST_CODE_PICK_DEVICES = 16

    object DataConfig {
        const val ALPHA_ENABLE_VIEW_VALUE: Float = 1f
        const val DURATION_FADE_VIEW_IN_MILLIS: Long = 3000
    }

    object SizeUtils {
        const val SIZE_LOW = "ldpi"
        const val SIZE_MEDIUM = "mdpi"
        const val SIZE_HDPI = "hdpi"
        const val SIZE_XHDPI = "xhdpi"
        const val SIZE_XXHDPI = "xxhdpi"
        const val SIZE_XXXHDPI = "xxxhdpi"
    }
}