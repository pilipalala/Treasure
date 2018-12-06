# 收藏的开源库


---

## [使用Android-Debug-Database](https://github.com/amitshekhariitbhu/Android-Debug-Database)

如何使用
在`build.gradle`添加如下：

`debugCompile 'com.amitshekhar.android:debug-db:1.0.0'`

debugCompile 的作用：只在你debug编译时起作用，当你release的时候就没必要使用它了。

这就完了，你不需要任何其他的代码啦。

下面当你在App启动的时候，你要注意查看下你的logcat，会有这么一行：
D/DebugDB: Open http://XXX.XXX.X.XXX:8080
把它复制到你电脑的浏览器(手机和电脑要在同一个局域网下)，你就可以看到你的App中的数据库，和shared preferences

---

## 1. [高斯模糊](https://github.com/mmin18/RealtimeBlurView)
适用于Android的实时模糊叠加层
``implementation 'com.github.mmin18:realtimeblurview:1.1.0'``

## 2. level-list 的坑
`android:maxLevel`的顺序要 从小到大排列

## 3. [一个关于时间处理的 包括两个时间计算，时区的计算](https://github.com/dlew/joda-time-android)
[Android Joda-Time使用](https://www.jianshu.com/p/92a131fa9dd5)
 `implementation 'net.danlew:android.joda:2.9.3.1'`

## 4. [一个Material 风格的 Dialog]( https://github.com/afollestad/material-dialogs)
`implementation 'com.afollestad.material-dialogs:core:2.0.0-rc2'`

![](https://raw.githubusercontent.com/afollestad/material-dialogs/master/art/showcase20.jpg)

## 5. [运行时权限处理 Permission helper](https://github.com/k0shk0sh/PermissionHelper)
``implementation 'com.github.k0shk0sh:PermissionHelper:1.1.0'``

## 6. [Android布局，支持扩展和折叠子视图](https://github.com/cachapa/ExpandableLayout)
``implementation 'net.cachapa.expandablelayout:expandablelayout:2.3' ``

## 7. [Image Picker](https://github.com/nguyenhoanglam/ImagePicker)
``implementation 'com.github.nguyenhoanglam:ImagePicker:1.1.2'``

## 8. [Pusher 推送](https://pusher.com/docs/android_quick_start)
```
    compile 'com.pusher:pusher-java-client:1.2.1'
    compile('com.crashlytics.sdk.android:crashlytics:2.6.5@aar') {
        transitive = true;
    }
```

## 9. [android-gif-drawable](https://github.com/koral--/android-gif-drawable)

``implementation 'pl.droidsonroids.gif:android-gif-drawable:1.2.16'``


```
    /**
     * Java 8 Stream support for pre Java8
     * */
    compile 'com.annimon:stream:1.0.9'

    /**
     * Stetho to debug app using Chrome Inspect
     * */
    compile 'com.facebook.stetho:stetho:1.3.1'
    compile 'com.facebook.stetho:stetho-okhttp3:1.3.1'
```
