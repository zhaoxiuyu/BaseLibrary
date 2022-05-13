package com.base.library.okhttp.cookie;

import java.io.Closeable;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.cache.DiskLruCache.Companion;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.io.FileSystem;

/**
 * 此类的作用在于兼用OkHttp版本  注意: 本类一定要用Java语言编写，kotlin将无法兼容新老版本
 * User: ljx
 * Date: 2020/5/17
 * Time: 15:28
 */
public class OkHttpCompat {

    private static String OKHTTP_USER_AGENT;

    public static void closeQuietly(Response response) {
        if (response == null) return;
        closeQuietly(response.body());
    }

    public static void closeQuietly(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable == null) continue;
            Util.closeQuietly(closeable);
        }
    }

    public static DiskLruCache newDiskLruCache(FileSystem fileSystem, File directory, int appVersion, int valueCount, long maxSize) {
        if (okHttpVersionCompare("4.3.0") >= 0) {
            return new DiskLruCache(fileSystem, directory, appVersion, valueCount, maxSize, TaskRunner.INSTANCE);
        } else if (okHttpVersionCompare("4.0.0") >= 0) {
            Companion companion = DiskLruCache.Companion;
            Class<? extends Companion> clazz = companion.getClass();
            try {
                Method create = clazz.getDeclaredMethod("create", FileSystem.class, File.class, int.class, int.class, long.class);
                return (DiskLruCache) create.invoke(companion, fileSystem, directory, appVersion, valueCount, maxSize);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            Class<DiskLruCache> clazz = DiskLruCache.class;
            try {
                Method create = clazz.getDeclaredMethod("create", FileSystem.class, File.class, int.class, int.class, long.class);
                return (DiskLruCache) create.invoke(null, fileSystem, directory, appVersion, valueCount, maxSize);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Please upgrade OkHttp to V3.12.0 or higher");
    }

    //okhttp版本比较，当前版本大于version2，返回 >0; 等于，返回=0; 否则，返回 <0
    public static int okHttpVersionCompare(String version2) {
        String[] okHttpUserAgentArr = getOkHttpUserAgent().split("/");
        String okhttpVersion = okHttpUserAgentArr[okHttpUserAgentArr.length - 1];
        return versionCompare(okhttpVersion, version2);
    }

    //获取OkHttp版本号
    public static String getOkHttpUserAgent() {
        if (OKHTTP_USER_AGENT != null) return OKHTTP_USER_AGENT;
        try {
            //4.7.x及以上版本获取userAgent方式
            Class<?> clazz = Class.forName("okhttp3.internal.Util");
            return OKHTTP_USER_AGENT = (String) clazz.getDeclaredField("userAgent").get(null);
        } catch (Throwable ignore) {
        }
        try {
            Class<?> clazz = Class.forName("okhttp3.internal.Version");
            try {
                //4.x.x及以上版本获取userAgent方式
                Field userAgent = clazz.getDeclaredField("userAgent");
                return OKHTTP_USER_AGENT = (String) userAgent.get(null);
            } catch (Exception ignore) {
            }
            //4.x.x以下版本获取userAgent方式
            Method userAgent = clazz.getDeclaredMethod("userAgent");
            return OKHTTP_USER_AGENT = (String) userAgent.invoke(null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return OKHTTP_USER_AGENT = "okhttp/4.2.0";
    }

    private static int versionCompare(String version1, String version2) {
        String[] versionArr1 = version1.split("\\.");
        String[] versionArr2 = version2.split("\\.");
        int minLen = Math.min(versionArr1.length, versionArr2.length);
        int diff = 0;
        for (int i = 0; i < minLen; i++) {
            String v1 = versionArr1[i];
            String v2 = versionArr2[i];
            diff = v1.length() - v2.length();
            if (diff == 0) {
                diff = v1.compareTo(v2);
            }
            if (diff != 0) {
                break;
            }
        }
        diff = (diff != 0) ? diff : (versionArr1.length - versionArr2.length);
        return diff;
    }
}