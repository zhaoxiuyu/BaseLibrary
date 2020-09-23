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

方法名1 | 说明
---|---
getGson | 获取Gson实例
getClass | 将数据解析成指定泛型并返回
getJsonString | 将指定类变成Json型数据返回
getMap | 将数据解析成Map
getMapObj | 将数据解析成为Map 对象
getList | 将数据解析成为List<Map<String, Any>>

- EncryptUtils -> 加密解密相关的工具类

方法名1 | 说明
---|---
string2MD5 | 生成32位md5码

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
