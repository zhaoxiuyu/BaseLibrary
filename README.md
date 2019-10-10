# BaseLibrary说明

------

自己经常使用的MVP架构项目，集成了常用的第三方开源库。

因为公司电脑文件加密，除了.kt后缀的文件，所以全部改成用kotlin写。

会不定期更新优化新内容上传上来。

使用时把代码下载下来 进行依赖，每个项目不同，可以方便进行修改。

**自己使用的项目，上传上来做备份,感兴趣的可以自己copy下来** 

[查看依赖了哪些库](https://github.com/zhaoxiuyu/BaseLibrary/blob/master/library/build.gradle)

[Contract如何写参考](https://github.com/zhaoxiuyu/BaseLibrary/blob/master/library/src/main/java/com/base/library/template/contract/Demo1Contract.kt)

[Presenter如何写参考](https://github.com/zhaoxiuyu/BaseLibrary/blob/master/library/src/main/java/com/base/library/template/presenter/Demo1Presenter.kt)

[Activity如何写参考](https://github.com/zhaoxiuyu/BaseLibrary/blob/master/library/src/main/java/com/base/library/template/ui/Demo1Activity.kt)

[Java-Activity如何写参考](https://github.com/zhaoxiuyu/BaseLibrary/blob/master/library/src/main/java/com/base/library/template/ui/JavaActivityDemo.kt)

[不想写Contract和Presenter,可以直接使用通用的](https://github.com/zhaoxiuyu/BaseLibrary/blob/master/library/src/main/java/com/base/library/template/ui/Demo2Activity.kt)


**数据库使用Room** 

[表](https://github.com/zhaoxiuyu/BaseLibrary/blob/master/library/src/main/java/com/base/library/database/entity/Test.kt)

[dao](https://github.com/zhaoxiuyu/BaseLibrary/blob/master/library/src/main/java/com/base/library/database/dao/TestDao.kt)

[工具类](https://github.com/zhaoxiuyu/BaseLibrary/blob/master/library/src/main/java/com/base/library/database/DataBaseUtils.kt)

[dataBase](https://github.com/zhaoxiuyu/BaseLibrary/blob/master/library/src/main/java/com/base/library/database/database/DataBase.kt)

**使用http网络请求** 

```java
        val bRequest = BRequest("url").apply {
            httpType = BRequest.POST //请求类型
            httpMode = BRequest.getOkGo //请求方式(OkGo,OkRx2,Retrofit2)
            silence = false //是否静默加载
            isFinish = false//是否销毁页面
            isSpliceUrl = false//是否强制将params的参数拼接到url后面,up系列与params系列混用
            isMultipart = false//是否强制使用multipart/form-data表单上传
            method = url //方法名(默认设置为URL)
            cacheMode = CacheMode.NO_CACHE//缓存模式
            cacheTime = -1L //缓存时长-1永不过期
            heads = null //请求头和参数  Map<String, String>
            params = null // key value 参数
            body = "" //upString
            tag = null //标识
        }
        mPresenter?.getData(bRequest)

```



