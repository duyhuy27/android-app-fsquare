package vn.md18.fsquareapplication.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import io.reactivex.disposables.CompositeDisposable
import vn.md18.fsquareapplication.R
import vn.md18.fsquareapplication.utils.Constant.REQUEST_CODE_WRITE_EXTERNAL_PERMISSION
import vn.md18.fsquareapplication.utils.view.DialogGotoSetting

class PermissionUtil {
    companion object {
        /**
         * Check permission of device storage
         */
        fun checkPermissionStorage(
            activity: Activity,
            mDisposable: CompositeDisposable,
            grantedPermission: GrantedPermissionCallBack
        ) {
            if (!AndPermission.hasPermissions(activity, Permission.Group.STORAGE)) {
                val rxPermissions = RxPermissions(activity as FragmentActivity)
                mDisposable.add(rxPermissions.request(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                    .subscribe { granted: Boolean? ->
                        if (!granted!!) {
                            val gotoSettingDialog = DialogGotoSetting(
                                activity,
                                R.style.AppThemeNew_DialogTheme,
                                activity.resources.getString(R.string.request_external_permission_title),
                                activity.resources.getString(R.string.request_external_permission_description)
                            )

                            gotoSettingDialog.setCallback {
                                if (activity.localClassName.contains("HomeEditActivity") || activity.localClassName.contains(
                                        "HomeEditActivity") || activity.localClassName.contains("FeedBackActivity")
                                ) {
                                    gotoDetailSettingsByRequestCode(
                                        activity,
                                        REQUEST_CODE_WRITE_EXTERNAL_PERMISSION
                                    )
                                } else {
                                    gotoDetailSettings(activity)
                                }
                            }
                            gotoSettingDialog.show()
                        } else {
                            grantedPermission.onGrantedPermission()
                        }
                    })
            } else {
                grantedPermission.onHadPermission()
            }
        }

        /**
         * Check permission of device microphone
         */
        fun checkPermissionMicrophone(
            activity: Activity,
            mDisposable: CompositeDisposable,
            grantedPermission: GrantedPermissionCallBack
        ) {
            if (!AndPermission.hasPermissions(
                    activity,
                    Permission.Group.MICROPHONE
                )
            ) {
                val rxPermissions = RxPermissions(activity as FragmentActivity)
                mDisposable.add(rxPermissions.request(
                    Manifest.permission.RECORD_AUDIO
                )
                    .subscribe { granted: Boolean? ->
                        if (!granted!!) {
                            val gotoSettingDialog = DialogGotoSetting(
                                activity,
                                R.style.AppThemeNew_DialogTheme,
                                activity.resources.getString(R.string.require_permission),
                                activity.resources.getString(
                                    R.string.require_custom_permission,
                                    activity.resources.getString(R.string.micro)
                                )
                            )
                            gotoSettingDialog.setCallback { gotoDetailSettings(activity) }
                            gotoSettingDialog.show()
                        } else {
                            grantedPermission.onGrantedPermission()
                        }
                    })
            } else {
                grantedPermission.onHadPermission()
            }
        }

        /**
         * Check permission of device location
         */
        fun checkPermissionLocation(
            activity: Activity,
            mDisposable: CompositeDisposable,
            grantedPermission: GrantedPermissionCallBack
        ) {
            if (!AndPermission.hasPermissions(activity, Permission.ACCESS_FINE_LOCATION)) {
                val rxPermissions = RxPermissions(activity as FragmentActivity)
                mDisposable.add(rxPermissions.request(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                    .subscribe { granted: Boolean? ->
                        if (!granted!!) {
                            val gotoSettingDialog = DialogGotoSetting(
                                activity,
                                R.style.AppThemeNew_DialogTheme,
                                activity.resources.getString(R.string.require_permission),
                                activity.resources.getString(R.string.require_permission_location)
                            )
                            gotoSettingDialog.setCallback {
                                if (activity.localClassName.contains("AddNewDeviceSelectFunctionActivity") ||
                                    activity.localClassName.contains("ListDeviceTypesActivity")
                                ) {
                                    gotoDetailSettingsByRequestCode(
                                        activity,
                                        Constant.REQUEST_CODE_ACCESS_FINE_LOCATION_AP_MODE
                                    )
                                } else {
                                    gotoDetailSettings(activity)
                                }
                            }
                            gotoSettingDialog.show()
                        } else {
                            grantedPermission.onGrantedPermission()
                        }
                    })
            } else {
                grantedPermission.onHadPermission()
            }
        }

        /**
         * Check permission of device location
         */
        @RequiresApi(Build.VERSION_CODES.Q)
        fun checkAllPermissionLocation(
            activity: Activity,
            mDisposable: CompositeDisposable,
            grantedPermission: GrantedPermissionCallBack
        ) {
            when {
                !AndPermission.hasPermissions(activity, Permission.ACCESS_BACKGROUND_LOCATION, Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION) -> {
                    val rxPermissions = RxPermissions(activity as FragmentActivity)
                    mDisposable.add(rxPermissions.request(
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION
                    )
                        .subscribe { granted: Boolean? ->
                            if (!granted!!) {
                                val gotoSettingDialog = DialogGotoSetting(
                                    activity,
                                    R.style.AppThemeNew_DialogTheme,
                                    activity.resources.getString(R.string.require_permission),
                                    activity.resources.getString(R.string.require_permission_location)
                                )
                                gotoSettingDialog.setCallback {
                                    if (activity.localClassName.contains("AddNewDeviceSelectFunctionActivity") ||
                                        activity.localClassName.contains("ListDeviceTypesActivity") || activity.localClassName.contains("AddSmartLightingActivity")
                                    ) {
                                        gotoDetailSettingsByRequestCode(
                                            activity,
                                            Constant.REQUEST_CODE_ACCESS_FINE_LOCATION_AP_MODE
                                        )
                                    } else {
                                        gotoDetailSettings(activity)
                                    }
                                }
                                gotoSettingDialog.show()
                            } else {
                                grantedPermission.onGrantedPermission()
                            }
                        })

                }
                else -> {
                    grantedPermission.onHadPermission()
                }
            }

        }

        fun checkPermissionCameraAndStorage(
            activity: Activity,
            mDisposable: CompositeDisposable,
            grantedPermission: GrantedPermissionCallBack
        ) {
            if (!AndPermission.hasPermissions(
                    activity, Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE,
                    Permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                val rxPermissions = RxPermissions(activity as FragmentActivity)
                mDisposable.add(rxPermissions.requestEachCombined(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                    .subscribe { permission ->
                        if (permission.granted) {
                            grantedPermission.onGrantedPermission()
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            val permissions = StringBuffer()
                            if (permission.name.contains(Permission.CAMERA) && !rxPermissions.isGranted(
                                    Permission.CAMERA
                                )
                            ) {
                                permissions.append(activity.resources.getString(R.string.camera))
                            }
                            if (permission.name.contains(Permission.READ_EXTERNAL_STORAGE) && !rxPermissions.isGranted(
                                    Permission.READ_EXTERNAL_STORAGE
                                ) || permission.name.contains(Permission.WRITE_EXTERNAL_STORAGE) && !rxPermissions.isGranted(
                                    Permission.WRITE_EXTERNAL_STORAGE
                                )
                            ) {
                                if (!TextUtils.isEmpty(permissions)) {
                                    permissions.append(", ")
                                }
                                permissions.append(activity.resources.getString(R.string.storage_new))
                            }
                            val gotoSettingDialog = DialogGotoSetting(
                                activity,
                                R.style.AppThemeNew_DialogTheme,
                                activity.resources.getString(R.string.require_permission),
                                activity.resources.getString(
                                    R.string.require_custom_permission,
                                    permissions
                                )
                            )
                            gotoSettingDialog.setCallback {
                                gotoDetailSettingsByRequestCode(
                                    activity,
                                    Constant.REQUEST_CODE_WRITE_EXTERNAL_PERMISSION
                                )
                            }
                            gotoSettingDialog.show()
                        } else {
                            gotoDetailSettingsByRequestCode(
                                activity,
                                Constant.REQUEST_CODE_WRITE_EXTERNAL_PERMISSION
                            )
                        }
                    }
                )
            } else {
                grantedPermission.onHadPermission()
            }
        }

        private fun gotoDetailSettings(activity: Activity) {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            activity.startActivity(intent)
        }

        fun gotoDetailSettingsByRequestCode(activity: Activity, requestCode: Int) {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            activity.startActivityForResult(intent, requestCode)
        }

        /**
         * When press allow button, will get onGrantedPermission
         * When already had permission, will get onHadPermission
         */
        interface GrantedPermissionCallBack {
            fun onGrantedPermission()
            fun onHadPermission()
        }
    }
}