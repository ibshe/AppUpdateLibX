# AppUpdateLib

### 介绍

开发过程中，发现很多更新库定制样式比较费事(如自定义dialog功能不完善，更新dialog和下载dialog耦合高等)，且功能不是特别契合需求(如使用dialogfragment封装难修改圆角、样式较难定制)，为加快开发速度提升效率，空闲时间简单封装了一个更新框架。

预览效果

![输入图片说明](http://103.45.138.168/apps/60d19a7546448_60d19a7601f37.gif "在这里输入图片标题")


### 使用说明

####  在你项目的根目录 build.gradle中加入如下配置：

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```




####   app的build.gradle中添加依赖：

```
dependencies {
	        implementation 'com.gitee.zkzyjs:AppUpdateLib:1.0.6'
	}
```

或：

```
dependencies {
	        implementation 'com.gitee.zkzyjs:AppUpdateLib:1.0.6'
	}
```

####   用法：

* 一行代码检查更新

```
new UpdateWrapper.Builder(this,mJsonUrl).build().start();
```
此方式使用默认的更新dialog和下载dialog样式，以及默认toast提示。

* 自定义更新dialog样式

```
class CustomDialog extends AbstractUpdateDialog {
        public CustomDialog(Context context, final String title, final String negivteTx, final String positiveTx, int layoutId) {
            super(context, title, negivteTx, positiveTx, layoutId, false, RadiusEnum.UPDATE_RADIUS_10, new DownlaodCallback() {
                @Override
                public void callback(int code, String message) {
                    Log.i(TAG,message);
                }
            });
            this.customOnCreate(new BindingCallback() {
                @Override
                public void bindingVh(DialogViewHolder holder) {
                    View view = holder.getConvertView();
                    view.setBackgroundResource(ScreenUtils.getDrawableId(RadiusEnum.UPDATE_RADIUS_30.getType()));
                    titleTv = view.findViewById(R.id.title);
                    contentTv = view.findViewById(R.id.message);
                    negtive = view.findViewById(R.id.negtive);
                    positive = view.findViewById(R.id.positive);
                    titleTv.setText(title);
                    contentTv.setText(BaseConfig.UPDATE_CONTENT);
                    negtive.setText(negivteTx);
                    positive.setText(positiveTx);
                    negtive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cancel();
                        }
                    });
                    positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            start();
                        }
                    });
                }
            });
        }
    }
```

使用自定义更新dialog：


```
new UpdateWrapper.Builder(this,mJsonUrl)
                        .customDialog(new CustomDialog(this,"发现新版本","取消","升级",R.layout.custom_update_dialog))
                        .checkEveryday(false)
                        .radius(RadiusEnum.UPDATE_RADIUS_30)
                        .build()
                        .start();
```


*  静默下载(无notification，后台下载完成直接跳转安装)


```
new DownloadWrapper(this,mDownloadUrl,true,RadiusEnum.UPDATE_RADIUS_30).start();
```

*  强制更新


```
new UpdateWrapper.Builder(this,mJsonUrl)
                        .isMustUpdate(true)
                        .build()
                        .start();
```

*  不弹或自定义更新dialog，下面方式调用直接下载

```
new DownloadWrapper(this,mDownloadUrl,false,RadiusEnum.UPDATE_RADIUS_10)
                        .start();
```

或者：


```
new DownloadDialog.Builder(this,mDownloadUrl,false)
                        .radius(RadiusEnum.UPDATE_RADIUS_10)
                        .build().start();
```

* 设置下载dialog样式

```
new DownloadDialog.Builder(this,mDownloadUrl,false)
                        .downloadCallback(new DownlaodCallback() {
                            @Override
                            public void callback(int code, String message) {
                            }
                        })
                        .build()
                        .setDialogStyle(R.drawable.update_bg_dark)
                        .setCancelColor(R.color.orange)
                        .setConfirmColor(R.color.dark_tx)
                        .setTitleColor(R.color.dark_tx)
                        .setLineColor(R.color.lineGray)
                        .setProgressStyle(getResources().getDrawable(R.drawable.custom_progressbar_bg))
                        .start();
```



* 主要参数

```
new UpdateWrapper.Builder(this,mJsonUrl)
                        .title("测试更新")//更新dialog标题
                        .negtiveText("取消")//更新dialog取消按钮
                        .radius(RadiusEnum.UPDATE_RADIUS_10)//更新和下载dialog圆角弧度同时生效
                        .positiveText("立即升级")//更新dialog确定按钮
                        .checkEveryday(false)//默认false 立即下载,true 每天最多检查一次。如今日已检查，则不再检查
                        .showNetworkErrorToast(true)//无网络提示
                        .showNoUpdateToast(true)//无更新提示
                        .isPost(false)//检查更新请求协议是否为POST，默认GET
                        .isMustUpdate(false)//是否强制更新
                        .backgroundDownload(false)//是否后台下载
                        .model(null)//非本地实体，不传默认为null
                        .downloadCallback(new DownlaodCallback() {//下载状态回调
                            @Override
                            public void callback(int code, String message) {
                                //code 1、后台下载；2、取消下载；3、下载完成；4、下载失败；
                                //Code 1. Background download; 2. Cancel the download; 3. Download completed; 4. Download failed;
                                Log.i(TAG,message);
                            }
                        })
                        .updateCallback(new UpdateWrapper.UpdateCallback() {//获取远端信息回调
                            @Override
                            public void res(VersionModel model, boolean hasNewVersion) {
                                Log.i(TAG,model.toString());
                            }
                        })
                        .build()
                        .start();
```



* 当做dialog使用，自定义dialog样式，自定义toast都OK



```
new BsDialog(this, R.layout.custom_update_dialog) {
            @Override
            public void onBindViewHolder(DialogViewHolder holder) {
                View view = holder.getConvertView();
                titleTv = view.findViewById(com.boge.update.R.id.title);
                contentTv = view.findViewById(com.boge.update.R.id.message);
                negtive = view.findViewById(com.boge.update.R.id.negtive);
                positive = view.findViewById(com.boge.update.R.id.positive);
                titleTv.setText(TextUtils.isEmpty(mTitle)?(TextUtils.isEmpty(BaseConfig.UPDATE_TITLE)?mContext.getString(R.string.update_lib_dialog_title):BaseConfig.UPDATE_TITLE):mTitle);
                contentTv.setText(BaseConfig.UPDATE_CONTENT);
                negtive.setText(TextUtils.isEmpty(mNegivteTx)?(TextUtils.isEmpty(BaseConfig.UPDATE_NEGITIVE)?mContext.getString(R.string.update_no_thanks):BaseConfig.UPDATE_NEGITIVE):mNegivteTx);
                positive.setText(TextUtils.isEmpty(mPositiveTx)?(TextUtils.isEmpty(BaseConfig.UPDATE_POSITIVE)?mContext.getString(R.string.update_sure):BaseConfig.UPDATE_POSITIVE):mPositiveTx);
                if(mRadius.getType() != 10){
                    view.setBackgroundResource(ScreenUtils.getDrawableId(mRadius.getType()));
                }
                negtive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cancel();
                    }
                });
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start();
                    }
                });
            }
        }.backgroundLight(0.5)
                .setCancelAble(true)
                .setCanceledOnTouchOutside(true)
                .showDialog();
```


### 鸣谢

1.  使用到chongheng.wang部分工具类


### 其他

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  博客地址：https://blog.csdn.net/m0_37824232/article/details/118102122
