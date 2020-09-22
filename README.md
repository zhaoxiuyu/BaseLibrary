[![](https://jitpack.io/v/zhaoxiuyu/BaseLibrary.svg)](https://jitpack.io/#zhaoxiuyu/BaseLibrary)

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.zhaoxiuyu:BaseLibrary:Tag'
	}
        

MathTool -> 数学计算类
    方法名	说明
    add	    加 返回double
    sub	    减 返回double
    mul	    乘 返回double
    div	    除 返回double
    round	小数位四舍五入 返回double
    roundStr	小数位四舍五入 返回String

ImageView
DynamicHeightImageView -> 可设置宽高比的图片
方法名	        说明
setHeightRatio	设置ImageView的宽高尺寸比例
getHeightRatio	获取设置的宽高比例


应用商店相关 -> AppStoreUtils.java -> Demo
getAppStoreIntent: 获取跳转应用商店意图

这些工具类需要补充进来
https://github.com/Blankj/AndroidUtilCode/blob/master/lib/subutil/README-CN.md  

