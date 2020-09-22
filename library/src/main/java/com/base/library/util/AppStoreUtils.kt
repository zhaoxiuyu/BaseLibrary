package com.base.library.util

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.blankj.utilcode.util.RomUtils
import com.blankj.utilcode.util.Utils

/**
 * 应用商店相关
 */
object AppStoreUtils {

    private const val TAG = "AppStoreUtils"

    private const val GOOGLE_PLAY_APP_STORE_PACKAGE_NAME = "com.android.vending"

    /**
     * 获取跳转到应用商店的 Intent
     *
     * @return 跳转到应用商店的 Intent
     */
    fun getAppStoreIntent(): Intent? {
        return getAppStoreIntent(Utils.getApp().packageName, false)
    }

    /**
     * 获取跳转到应用商店的 Intent
     *
     * @param isIncludeGooglePlayStore 是否包括 Google Play 商店
     * @return 跳转到应用商店的 Intent
     */
    fun getAppStoreIntent(isIncludeGooglePlayStore: Boolean): Intent? {
        return getAppStoreIntent(Utils.getApp().packageName, isIncludeGooglePlayStore)
    }

    /**
     * 获取跳转到应用商店的 Intent
     *
     * @param packageName 包名
     * @return 跳转到应用商店的 Intent
     */
    fun getAppStoreIntent(packageName: String): Intent? {
        return getAppStoreIntent(packageName, false)
    }

    /**
     * 获取跳转到应用商店的 Intent
     *
     * 优先跳转到手机自带的应用市场
     *
     * @param packageName              包名
     * @param isIncludeGooglePlayStore 是否包括 Google Play 商店
     * @return 跳转到应用商店的 Intent
     */
    fun getAppStoreIntent(
        packageName: String,
        isIncludeGooglePlayStore: Boolean
    ): Intent? {
        if (RomUtils.isSamsung()) { // 三星单独处理跳转三星市场
            val samsungAppStoreIntent = getSamsungAppStoreIntent(packageName)
            if (samsungAppStoreIntent != null) return samsungAppStoreIntent
        }
        if (RomUtils.isLeeco()) { // 乐视单独处理跳转乐视市场
            val leecoAppStoreIntent = getLeecoAppStoreIntent(packageName)
            if (leecoAppStoreIntent != null) return leecoAppStoreIntent
        }
        val uri: Uri = Uri.parse("market://details?id=$packageName")
        val intent = Intent()
        intent.data = uri
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val resolveInfos: List<ResolveInfo> = Utils.getApp().packageManager
            .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (resolveInfos == null || resolveInfos.size == 0) {
            Log.e(TAG, "No app store!")
            return null
        }
        var googleIntent: Intent? = null
        for (resolveInfo in resolveInfos) {
            val pkgName = resolveInfo.activityInfo.packageName
            if (GOOGLE_PLAY_APP_STORE_PACKAGE_NAME != pkgName) {
                if (isAppSystem(pkgName)) {
                    intent.setPackage(pkgName)
                    return intent
                }
            } else {
                intent.setPackage(GOOGLE_PLAY_APP_STORE_PACKAGE_NAME)
                googleIntent = intent
            }
        }
        if (isIncludeGooglePlayStore && googleIntent != null) {
            return googleIntent
        }
        intent.setPackage(resolveInfos[0].activityInfo.packageName)
        return intent
    }

    private fun go2NormalAppStore(packageName: String): Boolean {
        val intent = getNormalAppStoreIntent() ?: return false
        intent.data = Uri.parse("market://details?id=$packageName")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        Utils.getApp().startActivity(intent)
        return true
    }

    private fun getNormalAppStoreIntent(): Intent? {
        val intent = Intent()
        val uri: Uri = Uri.parse("market://details?id=" + Utils.getApp().getPackageName())
        intent.data = uri
        return if (getAvailableIntentSize(intent) > 0) {
            intent
        } else null
    }

    private fun getSamsungAppStoreIntent(packageName: String): Intent? {
        val intent = Intent()
        intent.setClassName(
            "com.sec.android.app.samsungapps",
            "com.sec.android.app.samsungapps.Main"
        )
        intent.data =
            Uri.parse("http://www.samsungapps.com/appquery/appDetail.as?appId=$packageName")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return if (getAvailableIntentSize(intent) > 0) {
            intent
        } else null
    }

    private fun getLeecoAppStoreIntent(packageName: String): Intent? {
        val intent = Intent()
        intent.setClassName(
            "com.letv.app.appstore",
            "com.letv.app.appstore.appmodule.details.DetailsActivity"
        )
        intent.action = "com.letv.app.appstore.appdetailactivity"
        intent.putExtra("packageName", packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return if (getAvailableIntentSize(intent) > 0) {
            intent
        } else null
    }

    private fun getAvailableIntentSize(intent: Intent): Int {
        return Utils.getApp().packageManager.queryIntentActivities(
            intent, PackageManager.MATCH_DEFAULT_ONLY
        ).size
    }

    private fun isAppSystem(packageName: String): Boolean {
        return if (TextUtils.isEmpty(packageName)) false else try {
            val pm: PackageManager = Utils.getApp().packageManager
            val ai = pm.getApplicationInfo(packageName, 0)
            ai != null && ai.flags and ApplicationInfo.FLAG_SYSTEM != 0
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        }
    }
}