### 笔记

#### SharedPreferences跨进程共享

```java
//模式为全局读写
public static final int READ_WRITE_MODE = Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE;

//Client端通过包名创建Context
Context c=context.createPackageContext(REMOTE_PACKAGE_NAME, Context.CONTEXT_IGNORE_SECURITY);
```

> Server和Client端模式都用READ_WRITE_MODE,Client端通过createPackageContext()获取远程的Context,其他写法不变

#### Error

> Error:All flavors must now belong to a named flavor dimension.

* 解决方案

```gradle
defaultConfig {
        applicationId "com.surfkj.surfsecurity"
        minSdkVersion 19
        targetSdkVersion 27
        versionCode 1
        versionName "1.0"
        flavorDimensions "versionCode"
    }
```

#### RelativeLayout两个控件填充布局

```xm

   <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:id="@+id/item_tv_title_more"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentRight="true" />

        <TextView
            android:id="@+id/textView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_toLeftOf="@id/item_tv_title_more"
            android:layout_toStartOf="@id/item_tv_title_more" />

    </RelativeLayout>
```

