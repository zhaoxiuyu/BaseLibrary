[![](https://jitpack.io/v/zhaoxiuyu/BaseLibrary.svg)](https://jitpack.io/#zhaoxiuyu/BaseLibrary)

==慢慢完善中，自己备份使用==

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

[project build.gradle](https://github.com/zhaoxiuyu/Proguards/blob/master/proguardslibrary/proguard-rules.pro)

[依赖库清单](https://github.com/zhaoxiuyu/BaseLibrary/blob/master/library/build.gradle)

### 库

    用不上的依赖库，可以排除依赖：
    implementation(project(':library')) {
        exclude group: 'com.daimajia.androidanimations', module: 'library'

#### 1. Activity 使用：
```
class DetailActivity : BActivity() {

    private val mBind by lazy { DetailsBinding.inflate(layoutInflater) }
    private val mViewModel by lazy { ViewModelProvider(this).get(DetailViewModel::class.java) }

    override fun initArgs(mIntent: Intent?) {
    }
    override fun initView() {
        // 设置Activity的布局
        setContentView(mBind.root)

        // 给 ContentView 的外面添加一个 通用的顶部导航栏,可以设置第二个参数是否沉浸式,默认是true
        setContentViewBar(mBind.root)
        // 通过getTitleBar()来操作导航栏
        getTitleBar().title = "标题"
    }
    override fun initData(savedInstanceState: Bundle?) {
        mBind.tv.text = "设置一下内容"
        mBind.tv.setOnClickListener {
            mViewModel.getArticle()
        }
    }
    override fun initObserve(): MutableList<BViewModel>? {
        mViewModel.articleLiveData.observe(this, Observer {
        })
        return mutableListOf(mViewModel)
    }
// 默认实现了一套提示框，你可以重写onSuccess onError ...
//    override fun onLoading(mRequest: RxRequest) {
//        super.onLoading(mRequest)
//    }
}
```
#### 2. Fragment 使用：
```
Fragment ： BFragment  // 继承 BFragment

方法参数类型改成Bundle即可
override fun initArgs(mArguments: Bundle?) { }
```
和Activity使用方式 方法都一样的。几乎没有区别。

#### 3. ViewModel 使用：
```
class DetailViewModel : BViewModel() {
    val articleLiveData by lazy { MutableLiveData<BResponse<WanArticle>>() }
    fun getArticle() {
		// 参数是请求url，域名在BConstant里面，可以动态赋值。
        val request = RxRequest(BConstant.article)
		// 获取请求类型，设置请求地址，具体使用和说明参考rxhttp
        request.httpGet().setDomainTowanandroidIfAbsent()
		// 发送请求，支持LiveData和回调多种参数。可以参考Demo3Activity
        getRepository().getResponse(request, WanArticle::class.java, articleLiveData)
    }
}
```

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

- BadgeDrawable -> 徽章：小红点，数字，文字。[参考](https://github.com/nekocode/Badge)

```
val drawable = BadgeDrawable.Builder()
    .type(BadgeDrawable.TYPE_NUMBER)
    .number("9") // 空串 "" 就是小圆点，通过 size 设置大小
    .text1("text1")
    .text2("text2")
    .textSize(12f)
    .textColor(0xFFFFFF)
    .typeFace(Typeface.DEFAULT_BOLD)
    .cornerRadius(10f)
    .strokeWidth(1)
    .badgeColor(0xFFFFFF)
    .padding(0f, 0f, 0f, 0f)
    .build()

通过调用toSpannable方法转为SpannableString,可以用来显示到TextView上
val ss = SpannableString(
    TextUtils.concat("TextView ", drawable.toSpannable(), " ", drawable.toSpannable(), " ")
        )
textView.text = ss
```
