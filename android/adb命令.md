* 卸载系统程序

```sh
#adb root		//进入系统中的根目录，获取系统权限
#adb remount	//重新挂载系统分区，使系统分区重新可写
#adb shell		//进入模拟器的shell模式
#cd system/app	//进入系统中的app应用目录
#ls
#cd UnistallApp	//进入你所想要删除的APK的目录中
#rm -fr UnistallApp.apk	//删除对应的apk文件(注：一定要加apk后缀名字)
#reboot			//重新启动手机(因为只有重启才能进行应用的删除,注意点!!!!)
```

* 列举所有应用包名

```sh
$ adb shell pm list packages
$ adb shell pm list packages | grep 'miui' //可以加上grep查询词
```

* 打开指定的软件

```sh
$ adb shell
$ am start -n ｛包(package)名｝/｛包名｝.{活动(activity)名称}
$ adb shell monkey -p 包名 -c android.intent.category.LAUNCHER 1
```

> 一般情况希望，一个Android应用对应一个工程。值得注意的是，有一些工程具有多个活动（activity），而有一些应用使用一个工程。例如：在Android界面中，Music和Video是两个应用，但是它们使用的都是packages/apps/Music这一个工程。而在这个工程的AndroidManifest.xml文件中，有包含了不同的活动（activity）。

a.启动浏览器 :

`am start -a android.intent.action.VIEW -d  http://www.google.cn/`

b.拨打电话 :

`am start -a android.intent.action.CALL -d tel:10086`

`am start -n com.microsoft.launcher/com.microsoft.launcher.Launcher`

* 终止程序运行:

```sh
adb shell am force-stop packageName
```

* 查看当前显示的activity

```
adb shell dumpsys activity | findstr "mFocusedActivity"
```

* 终端模拟器打开adb调试端口

```shell
su
setprop service.adb.tcp.port 5555
stop adbd
start adbd
```
