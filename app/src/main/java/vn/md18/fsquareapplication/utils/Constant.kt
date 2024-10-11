package vn.md18.fsquareapplication.utils

object Constant {
    const val KEY_LOGIN = "login"
    const val KEY_SIGNUP = "signup"
    const val KEY_TYPE = "type"
    const val KEY_EMAIL = "email"
    const val KEY_UID = "KEY_UID"
    const val KEY_USER_NAME = "KEY_USER_NAME"
    const val KEY_INTRO = "KEY_INTRO"
    const val KEY_TOKEN = "KEY_TOKEN"
    const val EMPTY_STRING = ""

    const val REQUEST_CODE_WRITE_EXTERNAL_PERMISSION = 11
    const val REQUEST_CODE_ACCESS_FINE_LOCATION_AP_MODE = 15
    const val REQUEST_CODE_PICK_DEVICES = 16
    const val CAMERA_WORKER_TAG = "cameraSYSTEM_NAME_work_tag"
    const val LOG_EXTENSION = ".log"
    const val TXT_EXTENTION = ".txt"
    const val SYSTEM_NAME = "Android"
    const val ANDROID_MEDIA = "/Android/media"
    const val TYPE_UPLOAD_LOG_FILE = "4"
    const val KEY_REQUEST = "KEY_REQUEST"
    object DataConfig {
        const val ALPHA_ENABLE_VIEW_VALUE: Float = 1f
        const val DURATION_FADE_VIEW_IN_MILLIS: Long = 3000
    }



    enum class BuildFlavor(val value: String) {
        Prod("prod"),
        Dev("dev"),
        Stag("stag"),
        SmarthomeDev("smarthomeDev"),
        SmarthomeStag("smarthomeStag"),
        CameraDev("cameraDev"),
        CameraStag("cameraStag"),
        WifiDev("wifiDev"),
        WifiStag("wifiStag"),
        Pilot("pilot"),
        Showroom("showroom"),
    }
    enum class ToastStatus {
        SUCCESS,
        FAILURE,
        WARNING,
        INFO
    }

    object SizeUtils {
        const val SIZE_LOW = "ldpi"
        const val SIZE_MEDIUM = "mdpi"
        const val SIZE_HDPI = "hdpi"
        const val SIZE_XHDPI = "xhdpi"
        const val SIZE_XXHDPI = "xxhdpi"
        const val SIZE_XXXHDPI = "xxxhdpi"
    }

    object TimeDateFormat {
        const val FORMAT_DATE = "dd/MM/yyyy"
        const val FORMAT_TIME = "HH:mm:ss"
        const val TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss"
        const val TIME_CREATED_FORMAT = "HH:mm dd/MM/yyyy"
        const val TIME_END_FORMAT = "dd-MM-yyyy - HH:mm:ss"
        const val TIME_DATE_FORMAT = "HH:mm:ss dd/MM/yyyy"
        const val FORMAT_HOUR = "hh:mm"
        const val FORMAT_HOUR_24 = "HH:mm"
        const val DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss"
        const val SPEED_TIME_FORMAT = "dd/MM/yyyy - HH:mm"
        const val FORMAT_MINUTE = "mm:ss"
        const val FORMAT_DATE_LOG = "yyyy-MM-dd"
        const val FORMAT_TIMESTAMP_LOG = "yyyyMMddHHmm"
    }


}