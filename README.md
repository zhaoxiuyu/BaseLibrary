[![](https://jitpack.io/v/zhaoxiuyu/BaseLibrary.svg)](https://jitpack.io/#zhaoxiuyu/BaseLibrary)

==以下暂未补全==

==CockroachUtil 异常拦截==

使用如下：

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	dependencies {
	        implementation 'com.github.zhaoxiuyu:BaseLibrary:Tag'
	}


### 库
    平常项目中用到的都集成进来了，如果用不上，可以排除依赖，比如：
    implementation(project(':library')) {
        exclude group: 'com.daimajia.androidanimations', module: 'library'
    }
    
    在你的project build.gradle 中加入插件
    // 事件总线
    classpath 'com.blankj:bus-gradle-plugin:2.6'
---

    // 已经整合的库
    // 卡片
    api 'androidx.cardview:cardview:1.0.0'
    // 多dex
    api 'androidx.multidex:multidex:2.0.1'
    api "androidx.viewpager2:viewpager2:1.0.0"
    //v7
    api 'androidx.appcompat:appcompat:1.3.0-alpha02'
    // api 'androidx.appcompat:appcompat:1.1.0-rc01'
    //v4
    api 'androidx.legacy:legacy-support-v4:1.0.0'
    api 'androidx.recyclerview:recyclerview:1.2.0-alpha05'
    //design
    api 'com.google.android.material:material:1.2.1'
    //百分比布局
    api 'androidx.percentlayout:percentlayout:1.0.0'
    //矢量
    api 'androidx.vectordrawable:vectordrawable:1.1.0'
    //约束布局
    api 'androidx.constraintlayout:constraintlayout:2.0.1'
    // Jetpack
    api "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
    api "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
    api "androidx.lifecycle:lifecycle-common-java8:2.2.0"
    api "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    api "androidx.lifecycle:lifecycle-viewmodel-savedstate:2.2.0"
    // 用于在 Service 中实现 LifecycleOwner 的帮助程序
    api "androidx.lifecycle:lifecycle-service:2.2.0"
    // ProcessLifecycleOwner 为整个应用程序进程 Application 提供了一个生命周期
    api "androidx.lifecycle:lifecycle-process:2.2.0"
    // sqlite 数据库
    api 'org.litepal.guolindev:core:3.2.1'
    // 工具类
    api 'com.blankj:utilcodex:1.29.0'// 柯基 AndroidUtilCode
    // MMKV
    api 'com.tencent:mmkv-static:1.2.2'
    // Kotlin
    api 'androidx.core:core-ktx:1.3.1'
    api "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.4.0"
    api 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9'
    api 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
    // 智能刷新框架：分包之后不会有默认的Header和Footer需要手动添加！还是原来的三种方法！
    api 'com.scwang.smart:refresh-layout-kernel:2.0.1'      //核心必须依赖
    api 'com.scwang.smart:refresh-header-classics:2.0.1'    //经典刷新头
    api 'com.scwang.smart:refresh-header-radar:2.0.1'       //雷达刷新头
    api 'com.scwang.smart:refresh-header-falsify:2.0.1'     //虚拟刷新头
    api 'com.scwang.smart:refresh-header-material:2.0.1'    //谷歌刷新头
    api 'com.scwang.smart:refresh-header-two-level:2.0.1'   //二级刷新头
    api 'com.scwang.smart:refresh-footer-ball:2.0.1'        //球脉冲加载
    api 'com.scwang.smart:refresh-footer-classics:2.0.1'    //经典加载
    // 网络请求相关
    api 'com.ljx.rxhttp:rxhttp:2.4.0'
    kapt 'com.ljx.rxhttp:rxhttp-compiler:2.4.0'
    api 'com.squareup.okhttp3:okhttp:4.8.1'
    api 'io.reactivex.rxjava3:rxjava:3.0.2'
    api 'io.reactivex.rxjava3:rxandroid:3.0.0'
    // 解决RxJava内存泄漏
    api 'com.ljx.rxlife3:rxlife-rxjava:3.0.0'
    // 图片配置
    api 'com.github.bumptech.glide:glide:4.11.0'
    kapt 'com.github.bumptech.glide:compiler:4.11.0'
    api 'com.github.bumptech.glide:okhttp3-integration:4.10.0'
    api 'jp.wasabeef:glide-transformations:4.1.0'
    // View动画效果大集合
    api 'com.daimajia.easing:library:2.0@aar'
    api 'com.daimajia.androidanimations:library:2.3@aar'
    // lottie
    api 'com.airbnb.android:lottie:3.4.0'
    // RecyclerView 列表动画
    api 'jp.wasabeef:recyclerview-animators:3.0.0'
    // 动画工具类
    api 'com.github.florent37:viewanimator:1.1.2'
    // Json相关
    api 'com.google.code.gson:gson:2.8.6'
    // 适配器
    api 'com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4'
    // 分割线
    api 'ckrjfrog.FlexItemDecoration:Decoration:1.1.3'
    // banner 轮播图
    api 'com.youth.banner:banner:2.1.0'
    // 提示框
    api 'com.lxj:xpopup:2.0.9'
    // SuperTextView
    api 'com.github.chenBingX:SuperTextView:3.2.5.99'
    // RWidgetHelper : 圆角，边框，Gradient背景渐变，控件State各个状态UI样式，阴影，水波纹
    api 'com.ruffian.library:RWidgetHelper-AndroidX:0.0.6'
    // 侧滑一个就够了
    api 'com.billy.android:smart-swipe-x:1.1.0'
    // 沉浸式
    api 'com.gyf.immersionbar:immersionbar:3.0.0'
    api 'com.gyf.immersionbar:immersionbar-components:3.0.0'
    api 'com.gyf.immersionbar:immersionbar-ktx:3.0.0'
    // 底部导航栏
    api 'com.ashokvarma.android:bottom-navigation-bar:2.2.0'
    // 日历
    api 'com.haibin:calendarview:3.6.9'
    api 'com.squareup:android-times-square:1.6.5@aar'
    // 混淆库
    api 'com.github.zhaoxiuyu:Proguards:v1.0.4'




### 工具类相关:

- 应用商店相关 -> AppStoreUtils

方法名 | 说明
---|---
getAppStoreIntent | 获取跳转应用商店意图

- 电池相关 -> BatteryUtils

方法名 | 说明
---|---
registerBatteryStatusChangedListener | 注册电池状态改变监听器
isRegisteredBatteryStatusChangedListener | 判断是否注册电池状态改变监听器
unregisterBatteryStatusChangedListener | 注销电池状态改变监听器


- 剪贴板相关 -> ClipboardUtils.java

方法名 | 说明
---|---
copyText   |复制文本到剪贴板
getText    |获取剪贴板的文本
copyUri    |复制 uri 到剪贴板
getUri     |获取剪贴板的 uri
copyIntent |复制意图到剪贴板
getIntent  |获取剪贴板的意图


- 坐标转换相关 -> CoordinateUtils.java

方法名 | 说明
---|---
bd09ToGcj02  | BD09 坐标转 GCJ02 坐标
gcj02ToBd09  | GCJ02 坐标转 BD09 坐标
gcj02ToWGS84 | GCJ02 坐标转 WGS84 坐标
wgs84ToGcj02 | WGS84 坐标转 GCJ02 坐标
bd09ToWGS84  | BD09 坐标转 WGS84 坐标
wgs84ToBd09  | WGS84 坐标转 BD09 坐标

- 国家相关 -> CountryUtils.java

方法名 | 说明
---|---
getCountryCodeBySim      | 根据 Sim 卡获取国家码
getCountryCodeByLanguage | 根据系统语言获取国家码
getCountryBySim          | 根据 Sim 卡获取国家
getCountryByLanguage     | 根据系统语言获取国家



- 危险相关 -> DangerousUtils.java

方法名 | 说明
---|---
installAppSilent     | 静默安装 App
uninstallAppSilent   | 静默卸载 App
shutdown             | 关机
reboot               | 重启
reboot2Recovery      | 重启到 recovery
reboot2Bootloader    | 重启到 bootloader
setMobileDataEnabled | 打开或关闭移动数据
sendSmsSilent        | 发送短信

- 定位相关 -> LocationUtils.java

方法名 | 说明
---|---
isGpsEnabled      | 判断 Gps 是否可用
isLocationEnabled | 判断定位是否可用
openGpsSettings   | 打开 Gps 设置界面
register          | 注册
unregister        | 注销
getAddress        | 根据经纬度获取地理位置
getCountryName    | 根据经纬度获取所在国家
getLocality       | 根据经纬度获取所在地
getStreet         | 根据经纬度获取所在街道
isBetterLocation  | 是否更好的位置
isSameProvider    | 是否相同的提供者

- 拼音相关 -> PinyinUtils.java

方法名 | 说明
---|---
ccs2Pinyin            | 汉字转拼音
ccs2Pinyin            | 汉字转拼音
getPinyinFirstLetter  | 获取第一个汉字首字母
getPinyinFirstLetters | 获取所有汉字的首字母
getSurnamePinyin      | 根据名字获取姓氏的拼音
getSurnameFirstLetter | 根据名字获取姓氏的首字母


- MathUtils -> 数学计算类

方法名 | 说明
---|---
add	     | 加 返回double
sub	     | 减 返回double
mul	     | 乘 返回double
div	     | 除 返回double
round	 | 小数位四舍五入 返回double
roundStr | 	小数位四舍五入 返回String

- ViewUtils -> View相关

方法名 | 说明
---|---
isFastClick | 是否快速点击

- SoundPoolUtils -> 短音频 + 震动

方法名 | 说明
---|---
playVideo | 播放短音频
startVibrator | 震动时间
startVideoAndVibrator | 播放短音频+震动时间
release | 释放相应的资源

- MMKVUtils -> MMKV工具类

方法名 | 说明
---|---
put | MMKV中写入数据
getBool | MMKV 中读取 Boolean
getStr | MMKV 中读取 String
getInt | MMKV 中读取 Int
getLong | MMKV 中读取 Long
getFloat | MMKV 中读取 Float
getBytes | MMKV 中读取 Bytes
removeValueForKey | MMKV 中移除该 Key
removeValuesForKeys | MMKV 中移除多个 Key

- RxCacheUtils -> 使用RxJava进行磁盘缓存相关

方法名 | 说明
---|---
putCache | 缓存中写入数据
getCacheString | 缓存中读取 String

- JsonTool -> Json解析,拼装类

方法名 | 说明
---|---
getGson | 获取Gson实例
getClass | 将数据解析成指定泛型并返回
getJsonString | 将指定类变成Json型数据返回
getMap | 将数据解析成Map
getMapObj | 将数据解析成为Map 对象
getList | 将数据解析成为List<Map<String, Any>>

- EncryptUtils -> 加密解密相关的工具类

方法名 | 说明
---|---
string2MD5 | 生成32位md5码

- HandlerLifecycle -> 自动在onDestroy的时候移除Handler的消息，避免内存泄漏

方法名 | 说明
---|---
HandlerLifecycle(this) | 初始化

### View相关

- DynamicHeightImageView -> 可设置宽高比的图片

方法名 | 说明
---|---
setHeightRatio | 设置ImageView的宽高尺寸比例
getHeightRatio | 获取设置的宽高比例


- BTitleBar -> 自定义操作栏,该View此处只提供大概说明,详细使用时单独修改

方法名 | 说明
---|---
getIvLeft | 获取左边 ImageView
getTvLeft | 获取左边 TextView
getIvCenter | 获取中间 ImageView
getTvCenter | 获取中间 TextView
getIvEnd | 获取右侧 ImageView
getTvEnd | 获取右侧 TextView
setOnSurplusListener | 倒计时回调
startSurplus | 开始倒计时
stopSurplus | 停止倒计时
