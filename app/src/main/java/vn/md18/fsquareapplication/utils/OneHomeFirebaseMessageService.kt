package vn.md18.fsquareapplication.utils

import android.app.NotificationManager
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import vn.md18.fsquareapplication.di.component.datamanager.DataManager
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_AUTO_UPDATE_FW
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_DATA_BODY
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_DEVICE_IDS_MANUAL_UPDATE_FIRMWARE
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_FORCE_UPDATE_FW
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_RESET_UPDATE_FIRMWARE
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_TOTAL_NEW
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_TYPE
import vn.md18.fsquareapplication.utils.fslogger.FSLogger
import javax.inject.Inject

@AndroidEntryPoint
class OneHomeFirebaseMessageService : FirebaseMessagingService() {

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var gson: Gson

    /**
     *  Called when a new token for the default Firebase project is generated.
     */
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Timber.e("Change Token -> $p0")
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val data = remoteMessage.data
        val notifyType = data[NOTIFICATION_TYPE]
        val totalNew = data[NOTIFICATION_TOTAL_NEW]
        FSLogger.e("Huynd: Data Notification -> $data")
        if (!totalNew.isNullOrEmpty()) {
            val total = totalNew.toInt()
            FirebaseEvent.serviceEvent.postValue(
                Event(
                    Pair(
                        Constant.NotificationEvent.NotificationTotalNew,
                        total
                    )
                )
            )
        }

        val notificationManager =
            ContextCompat.getSystemService(baseContext, NotificationManager::class.java)
        val displayBanner = remoteMessage.notification != null  // Nếu trả về data mà ko có notification thì ko show banner. VD: Force update gateway
        notificationManager?.let {
            NotificationUtils.config(application)
            NotificationUtils.notifyByData(application, data, true)
        }

        notifyType?.let { type ->
            when (type.toInt()) {
                Constant.NotificationType.TYPE_UPDATE_FIRM_WARE -> {
                    val autoUpdateDevices = data[NOTIFICATION_AUTO_UPDATE_FW]
                    try {
                        val listType = object : TypeToken<List<Int?>?>() {}.type
                        val listId = gson.fromJson<ArrayList<Int>>(autoUpdateDevices, listType)
                        val resultUpgradeFirmware = data[NOTIFICATION_RESET_UPDATE_FIRMWARE]
                        val listDevicesHaveNewFirmware = data[NOTIFICATION_DEVICE_IDS_MANUAL_UPDATE_FIRMWARE]

                        when {
                            !listId.isNullOrEmpty() -> {
                                FirebaseEvent.serviceEvent.postValue(
                                    Event(
                                        Pair(
                                            Constant.NotificationEvent.NotificationUpgradeFirmware, listId
                                        )
                                    )
                                )
                                FirebaseEvent.firebaseMessageFirmwareEvent.postValue(FireBaseEventState.EventAutoUpdate(listId))
                            }

                            !resultUpgradeFirmware.isNullOrEmpty() -> {
                                val statusUpgrade = resultUpgradeFirmware.toInt()
                                val updateFwInfo =
                                    Pair(statusUpgrade, data)
                                FirebaseEvent.serviceEvent.postValue(
                                    Event(
                                        Pair(
                                            Constant.NotificationEvent.NotificationUpgradeFirmware,
                                            updateFwInfo
                                        )
                                    )
                                )
                                FirebaseEvent.firebaseMessageFirmwareEvent.postValue(FireBaseEventState.EventResultUpdate(updateFwInfo))
                            }

                            else -> {
                                val listDevicesIdNewFirm = gson.fromJson<ArrayList<Int>>(listDevicesHaveNewFirmware, listType)

                                FirebaseEvent.firebaseMessageFirmwareEvent.postValue(FireBaseEventState.EventNewFirmUpdate(listDevicesIdNewFirm))
                            }
                        }

                        data.entries.forEach {
                            Timber.e("Data Notification Upgrade Firmware -> $it")
                        }
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }

                }

                Constant.NotificationType.TYPE_FORCE_UPDATE_FIRMWARE -> {
                    val forceUpdateDevices = data[NOTIFICATION_FORCE_UPDATE_FW]

                    try {
                        val listType = object : TypeToken<List<Int?>?>() {}.type
                        val listForceUpdate = gson.fromJson<ArrayList<Int>>(forceUpdateDevices, listType)
                        if (!listForceUpdate.isNullOrEmpty()) {
                            FirebaseEvent.serviceEvent.postValue(
                                Event(
                                    Pair(
                                        Constant.NotificationEvent.NotificationForceUpdateFirmware,
                                        listForceUpdate
                                    )
                                )
                            )
                        }

                        data.entries.forEach {
                            Timber.e("Data Notification Upgrade Firmware -> $it")
                        }
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }

                }

                Constant.NotificationType.TYPE_DETECT_MOTION_CAMERA -> {
                    FirebaseEvent.serviceEvent.postValue(
                        Event(
                            Pair(
                                Constant.NotificationEvent.NotificationDetectMotion,
                                data
                            )
                        )
                    )
                }
                Constant.NotificationType.TYPE_DETECT_HUMAN_MOTION -> {
                    FirebaseEvent.serviceEvent.postValue(
                        Event(
                            Pair(
                                Constant.NotificationEvent.NotificationDetectHuman,
                                data
                            )
                        )
                    )
                }
                Constant.NotificationType.TYPE_DETECT_MOVE_SENSOR,
                Constant.NotificationType.TYPE_CLOSE_DOOR,
                Constant.NotificationType.TYPE_OPEN_DOOR,
                Constant.NotificationType.TYPE_SMOKE,
                Constant.NotificationType.TYPE_AUTO_OR_CONTEXT,
                Constant.NotificationType.TYPE_BATTERY_LOWER,
                Constant.NotificationType.TYPE_POWER_INCREASE,
                Constant.NotificationType.TYPE_TEMP,
                Constant.NotificationType.TYPE_HUMIDITY,
                Constant.NotificationType.TYPE_MAINTAIN,
                Constant.NotificationType.TYPE_NEW_VERSION,
                Constant.NotificationType.TYPE_FORCE_UPDATE_VERSION,
                Constant.NotificationType.TYPE_OTHER -> {
                    FirebaseEvent.serviceEvent.postValue(
                        Event(
                            Pair(
                                Constant.NotificationEvent.NewNotificationByType,
                                type.toInt()
                            )
                        )
                    )
                }

//                Constant.NotificationType.TYPE_ACCEPT_SHARE_HOME -> {
//                    val content = data[NOTIFICATION_BODY]
//                    FirebaseEvent.shareHomeEvent.postValue(content)
//                }
                Constant.NotificationType.TYPE_LEAVE_HOME -> {
                    FirebaseEvent.serviceEvent.postValue(
                        Event(
                            Pair(
                                Constant.NotificationEvent.NotificationMemberLeaveHome,
                                type.toInt()
                            )
                        )
                    )
//                    val content = data[NOTIFICATION_BODY]
//                    FirebaseEvent.leavingHomeEvent.postValue(content)
                }
                Constant.NotificationType.TYPE_VIDEO_CALL -> {
                    val dataCall = data[NOTIFICATION_DATA_BODY] //Get data call from be
                    Timber.e("Data Call: $dataCall")
                    FirebaseEvent.serviceEvent.postValue(
                        Event(
                            Pair(
                                Constant.NotificationEvent.NotificationVideoCallType,
                                dataCall.toString()
                            )
                        )
                    )
                }

                Constant.NotificationType.TYPE_SWITCH_ACCOUNT -> {
                    val dataSwitchAccount = data[NOTIFICATION_DATA_BODY]
                    Timber.e("Data Switch Account: $dataSwitchAccount")
                    FirebaseEvent.serviceEvent.postValue(
                        Event(
                            Pair(
                                Constant.NotificationEvent.NotificationSwitchAccountType,
                                dataSwitchAccount.toString()
                            )
                        )
                    )
                }

                else -> {
                    GlobalScope.launch(Dispatchers.Main) {
                        FirebaseEvent.serviceEvent.value = Event((Pair(Constant.NotificationEvent.NotificationNone, "HelloTest")))
                    }
                }
            }
        }
    }
}

sealed class FireBaseEventState {
    data class EventResultUpdate<out T>(val data: T) : FireBaseEventState()
    data class EventAutoUpdate<out T>(val data: T) : FireBaseEventState()
    data class EventNewFirmUpdate<out T>(val data: T) : FireBaseEventState()
}