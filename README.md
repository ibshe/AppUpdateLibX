# AppUpdateLib

### 介绍

开发过程中，发现很多更新库定制样式比较费事(如自定义dialog功能不完善，更新dialog和下载dialog耦合高等)，且功能不是特别契合需求(如使用dialogfragment封装难修改圆角、样式较难定制)，为加快开发速度提升效率，空闲时间简单封装了一个更新框架。

预览效果

![输入图片说明](http://103.45.138.168/apps/60d19a7546448_60d19a7601f37.gif "在这里输入图片标题")


### 使用说明

####  在你项目的根目录 build.gradle中加入如下配置：

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

####   app的build.gradle中添加依赖：

dependencies {
	        implementation 'com.gitee.zkzyjs:AppUpdateLib:1.0.6'
	}
或：
dependencies {
	        implementation 'com.gitee.zkzyjs:AppUpdateLib:1.0.6'
	}


####   用法：

* 一行代码检查更新

```
new UpdateWrapper.Builder(this,mJsonUrl).build().start();
```
此方式使用默认的更新dialog和下载dialog样式，以及默认toast提示。

* 

### 鸣谢

1.  使用到chongheng.wang部分工具类


### 其他

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  博客地址：https://blog.csdn.net/m0_37824232/article/details/118102122
