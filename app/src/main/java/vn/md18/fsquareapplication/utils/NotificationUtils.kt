/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vn.md18.fsquareapplication.utils

import android.app.ActivityManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Bundle
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.yanzhenjie.permission.util.StringUtils
import io.karn.notify.Notify
import timber.log.Timber
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.features.main.ui.MainActivity
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_BODY
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_CLICK_ACTION
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_DATA_BODY
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_ID
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_ITEM_ID
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_MODEL_CODE
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_TITLE
import vn.md18.fsquareapplication.utils.Constant.NotificationContentMapping.NOTIFICATION_TYPE
import java.util.*
/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */

class NotificationUtils {
    companion object {
        val TAG: String = NotificationUtils::class.java.simpleName
        fun config(context: Context) {
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            Notify.defaultConfig {
                header {
                    color = ContextCompat.getColor(context, R.color.light_blue)
                    icon = R.drawable.logo_app
                }
                alerting(Notify.CHANNEL_DEFAULT_KEY) {
                    lightColor = Color.RED
                    if (defaultSoundUri != null) {
                        sound = defaultSoundUri
                    }
                    channelImportance = NotificationManagerCompat.IMPORTANCE_DEFAULT
                }
            }
        }

        fun notifyDefault(context: Context) {
            io.karn.notify.Notify.with(context)
                .content {
                    title = "Title"
                    text = "Content"
                }
                .stackable {
                    key = "test_key"
                    summaryContent = "test summary content"
                    summaryTitle = { "Summary title" }
                    summaryDescription = { count -> "$count new notifications." }
                }
                .show()
        }

        fun notifyByData(context: Context, data: Map<String, String>, displayBanner: Boolean = true) {
            val content: String? = data[NOTIFICATION_BODY]
            val title: String? = data[NOTIFICATION_TITLE]
            var notificationId: String? = data[NOTIFICATION_ID]
            val bodyData: String? = data[NOTIFICATION_DATA_BODY]
            val objectId: String? = data[NOTIFICATION_ITEM_ID]
            val notificationType: String? = data[NOTIFICATION_TYPE]
            val action: String? = data[NOTIFICATION_CLICK_ACTION]
            val modelCode: String? = data[NOTIFICATION_MODEL_CODE]

            Timber.e(TAG, "itemId:$objectId")
            Timber.e(TAG, "notificationId:$notificationId")

            val activityManager: ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val taskList: List<ActivityManager.RunningTaskInfo> = activityManager.getRunningTasks(10)
            val largeIconBitmap =
                BitmapFactory.decodeResource(context.resources, R.drawable.logo_app)

            if (!displayBanner) {
                return
            }

            Notify.with(context)
                .asBigText {
                    this.title = title
                    text = content
                    expandedText = content
                    bigText = ""
                    if (largeIconBitmap != null) {
                        largeIcon = largeIconBitmap
                    }
                }
                .meta {
                }
                .show()
        }

        fun notifyTextList(context: Context) {
            Notify.with(context)
                .asTextList {
                    lines = listOf(
                        "New 1!",
                        "New 2!",
                        "New 3!"
                    )
                    title = "New menu items!"
                    text = lines.size.toString() + " new dessert menu items found."
                }
                .show()

        }

        fun notifyBigText(context: Context) {
            Notify.with(context)
                .asBigText {
                    title = "Title"
                    text = "Content"
                    expandedText = "This is a expandedText."
                    bigText =
                        "TODO"
                }
                .show()
        }


        private fun putExtraKeyDevice(target: Intent, key: String, deviceId: String?) {
            val bundleData = Bundle().apply {
                deviceId?.toLong()?.let {
                    putLong(
                        key, it
                    )
                }
            }

            target.putExtra(Constant.KEY_BUNDLE, bundleData)
        }

    }
}
