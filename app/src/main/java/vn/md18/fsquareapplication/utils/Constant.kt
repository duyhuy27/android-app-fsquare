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
    const val KEY_EDIT_PROFILE = "EDIT_PROFILE"
    const val KEY_ADDRESS = "ADDRESS"
    const val KEY_NEW_ADDRESS = "NEW_ADDRESS"
    const val KEY_SECURITY = "SECURITY"
    const val KEY_LANGUAGE = "LANGUAGE"
    const val KEY_POLICY = "PRIVACY_POLICY"
    const val KEY_NOTIFICATION = "NOTIFICATION"
    const val KEY_CONTACT = "CONTACT"
    const val KEY_PAYMENT = "PAYMENT"
    const val KEY_ABOUT = "ABOUT"
    const val KEY_SEND_TOKEN_FIREBASE_TO_BE = "KEY_SEND_TOKEN_FIREBASE_TO_BE"
    const val KEY_TOKEN_FIREBASE = "KEY_TOKEN_FIREBASE"
    const val PENDING = "Pending"
    const val PROCESSING = "Processing"
    const val SHIPPED = "Shipped"
    const val DELIVERED = "Delivered"
    const val CANCELED = "Canceled"

    const val VIET_QR = "VIET_QR"
    const val APPLE_PAY = "APPLE_PAY"
    const val MASTER_CARD = "MASTER_CARD"
    const val VISA = "VISA"
    const val PAYMENT_METHOD = "PAYMENT_METHOD"

    const val KEY_BUNDLE = "KEY_BUNDLE"
    const val KEY_PRODUCT = "KEY_PRODUCT"


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
    const val KEY_UPDATE_SUCCESS = "updateSuccess"
    const val KEY_ADD_SUCCESS = "addSuccess"
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
    object NotificationType {
        const val NOTIFICATION_TOTAL_NEW = 800
        const val NEW_NOTIFICATION_BY_TYPE = 900
        const val NOTIFICATION_ALL_EVENT = 101
        const val TYPE_NONE = -1
        const val TYPE_UPDATE_FIRM_WARE = 0
        const val TYPE_FORCE_UPDATE_FIRMWARE = 31
        const val TYPE_DETECT_MOTION_CAMERA = 1
        const val TYPE_DETECT_HUMAN_MOTION = 29
        const val TYPE_AUTO_OR_CONTEXT = 3
        const val TYPE_CHILD_DEVICE_OUT_NETWORK = 4
        const val TYPE_OPEN_DOOR = 5
        const val TYPE_CLOSE_DOOR = 6
        const val TYPE_DETECT_MOVE_SENSOR = 7
        const val TYPE_TEMP = 8
        const val TYPE_HUMIDITY = 9
        const val TYPE_POWER_INCREASE = 10
        const val TYPE_SMOKE = 11
        const val TYPE_BATTERY_LOWER = 12
        const val TYPE_SHARING_HOME = 13
        const val TYPE_LEAVE_HOME = 14
        const val TYPE_MAINTAIN = 15
        const val TYPE_NEW_VERSION = 16
        const val TYPE_ACCEPT_SHARE_HOME = 28
        const val TYPE_OTHER = 17
        const val TYPE_FORCE_UPDATE_VERSION = 18
        const val TYPE_SERVICE_PAYMENT = 20
        const val TYPE_BABY_CRY_ALARM = 32
        const val TYPE_CAR_ALARM = 34
        const val TYPE_PET_ALARM = 35
        const val TYPE_LINGER = 36
        const val TYPE_SOUND_ALARM = 2
        const val TYPE_OFFLINE = 37
        const val APP_MARKET_LINK = "market://details?id=%s"
        const val APP_GOOGLE_PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=%s"
        const val TYPE_VIDEO_CALL = 38
        const val TYPE_OTHER_NOTIFICATION = -2
        const val TYPE_SWITCH_ACCOUNT = 42
        const val TYPE_MISS_CALL = 41
    }
    enum class NotificationEvent(value: Int) {
        NotificationNone(NotificationType.TYPE_NONE),
        NotificationUpgradeFirmware(NotificationType.TYPE_UPDATE_FIRM_WARE),
        NotificationForceUpdateFirmware(NotificationType.TYPE_FORCE_UPDATE_FIRMWARE),
        NotificationDetectMotion(NotificationType.TYPE_DETECT_MOTION_CAMERA),
        NotificationDetectHuman(NotificationType.TYPE_DETECT_HUMAN_MOTION),
        NotificationAutoOrContext(NotificationType.TYPE_AUTO_OR_CONTEXT),
        NotificationChildDeviceOutNetwork(NotificationType.TYPE_CHILD_DEVICE_OUT_NETWORK),
        NotificationDeviceOpenDoor(NotificationType.TYPE_OPEN_DOOR),
        NotificationDeviceCloseDoor(NotificationType.TYPE_CLOSE_DOOR),
        NotificationDeviceMoveSensor(NotificationType.TYPE_DETECT_MOVE_SENSOR),
        NotificationDeviceTemp(NotificationType.TYPE_TEMP),
        NotificationDeviceHumidity(NotificationType.TYPE_HUMIDITY),
        NotificationDevicePowerIncrease(NotificationType.TYPE_POWER_INCREASE),
        NotificationDeviceSmoke(NotificationType.TYPE_SMOKE),
        NotificationDeviceBatteryLow(NotificationType.TYPE_BATTERY_LOWER),
        NotificationMemberSharingHome(NotificationType.TYPE_SHARING_HOME),
        NotificationMemberLeaveHome(NotificationType.TYPE_LEAVE_HOME),
        NotificationMaintain(NotificationType.TYPE_MAINTAIN),
        NotificationNewVersion(NotificationType.TYPE_NEW_VERSION),
        NotificationOther(NotificationType.TYPE_OTHER),
        NotificationTotalNew(NotificationType.NOTIFICATION_TOTAL_NEW),
        NewNotificationByType(NotificationType.NEW_NOTIFICATION_BY_TYPE),
        NotificationVideoCallType(NotificationType.TYPE_VIDEO_CALL),
        NotificationSwitchAccountType(NotificationType.TYPE_SWITCH_ACCOUNT);
        companion object {
            fun getTypeQuery(typeNotification: String): String {
                return "type=in=($typeNotification)"
            }
        }
    }
    const val URL_PAYMENT_SUCCESS = "https://www.baokim.vn/?created_at="
    const val KEY_ORDER_ID = "KEY_ORDER_ID"
    const val KEY_ORDER_CLIENT_CODE = "KEY_ORDER_CLIENT_CODE"
    const val KEY_SEARCH_QUERY = "KEY_SEARCH_QUERY"
    object NotificationContentMapping {
        const val NOTIFICATION_TYPE = "notificationType"
        const val NOTIFICATION_AUTO_UPDATE_FW = "deviceIdsAutoUpdateFW"
        const val NOTIFICATION_FORCE_UPDATE_FW = "deviceIdsForceUpdateFw"
        const val NOTIFICATION_TOTAL_NEW = "totalIsNew"
        const val NOTIFICATION_RESET_UPDATE_FIRMWARE = "resultUpdateFw"
        const val NOTIFICATION_DEVICE_IDS_MANUAL_UPDATE_FIRMWARE = "deviceIdsManualUpdateFW"
        const val NOTIFICATION_DEVICE_NAME = "deviceName"
        const val NOTIFICATION_DEVICE_IDS = "deviceId"
        const val NOTIFICATION_DEVICE_UID = "uid"
        const val NOTIFICATION_BODY = "body"
        const val NOTIFICATION_ITEM_ID = "itemId"
        const val NOTIFICATION_CLICK_ACTION = "click_action"
        const val NOTIFICATION_MODEL_CODE = "modelCode"
        const val NOTIFICATION_ID = "notificationId"
        const val NOTIFICATION_TITLE = "title"
        const val NOTIFICATION_UPDATE_SJ_TO_TECH = "updateSJToVnptTech"
        const val NOTIFICATION_DATA_BODY = "data"
    }

}