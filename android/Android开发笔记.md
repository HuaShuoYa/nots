#### 设定屏幕方向

1. 修改AndroidManifest.xml

   在AndroidManifest.xml的activity中加入:

   横屏：

   ```xml
   android:screenOrientation="landscape"
   ```

   竖屏：

   ```xml
   android:screenOrientation="portrait"
   ```

2. setRequestOrientation

   横屏：

   ```java
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
   ```

   竖屏：

   ```java
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
   ```

#### 禁止屏幕旋转后重置Activity

> 屏幕旋转后会强制调用**Activity.onCreate**方法，所以会重置Activity
>
> 禁止方法：

``` xml
android:configChanges="orientation|keyboardHidden|screenSize"  
```

