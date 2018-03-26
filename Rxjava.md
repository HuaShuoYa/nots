## Rxjava笔记

### 入门介绍:[地址](http://blog.csdn.net/carson_ho/article/details/78179340)

* 定义：`Rxjava`是一个基于事件流，实现异步操作的库

* 特点：基于事件流的链式调用，所以使得`Rxjava`:

  * 逻辑间接
  * 实现优雅
  * 使用简单

  更重要的是，随着程序逻辑的复杂性提高，它依旧能够保持简介&优雅

* 原理介绍：

  1. `Rxjava`原理：基于一种扩展的观察者模式
  2. `Rxjava`的扩展观察者模式有4个角色：

  | 角色                   | 作用                       |
  | ---------------------- | -------------------------- |
  | 被观察者（Observable） | 产生事件                   |
  | 观察者（Observer）     | 接收事件，并给出响应动作   |
  | 订阅（Subscribe）      | 连接 被观察者&观察者       |
  | 事件（Event）          | 被观察者&观察者 沟通的载体 |

  被观察者`Observable`通过订阅`Subscribe`按顺序发送事件给观察者`Observer`，观察者按顺序接收事件并作出对应的相应动作

### 操作符:[地址](http://blog.csdn.net/carson_ho/article/details/78246732)

* `Rxjava`提供了丰富&功能强大的操作符，几乎能完成所有的功能需求

![](http://upload-images.jianshu.io/upload_images/944365-b02adb46075329b0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 基本创建

* 需求场景

  完整的创建被观察者对象

* 对应操作符类型

**create（）**

 * 作用

   完整创建1个被观察对象（Observable）

> RxJava中创建被观察者对象最基本的操作符

##### 快速创建&发送事件

* 需求场景

  快速的创建被观察者对象

* 对应的操作符类型

**just（）**

* 作用

  1.快速创建1个被观察者对象（Observable）

  2.发送事件的特点：直接发送传入的事件
> 最多只能发送10个参数
* 应用场景
  快速创建被观察对象（Observable）&发送10个事件

**fromArray()**

* 作用

  1.快速创建一个被观察者对象（Observable）

  2.发送事件的特点：直接发送传入的数组数据

> 会将数组中的数据转换为Observable对象

* 应用场景

  1.快速创建被观察对象（Observable）&发送10个以上事件（数组形式）

  2.数组元素遍历

**fromIterable()**

* 作用

  1.快速创建1个被观察者对象（Observable）

  2.发送事件的特点：直接发送传入的集合List数据

> 会将数据中的数据转换为Observable对象

* 应用场景

		1.快速创建被观察对象（Observable）&发送10个以上事件（数组形式）

		2.数组元素遍历

**额外**

```java
// 下列方法一般用于测试使用

<-- empty()  -->
// 该方法创建的被观察者对象发送事件的特点：仅发送Complete事件，直接通知完成
Observable observable1=Observable.empty(); 
// 即观察者接收后会直接调用onCompleted（）

<-- error()  -->
// 该方法创建的被观察者对象发送事件的特点：仅发送Error事件，直接通知异常
// 可自定义异常
Observable observable2=Observable.error(new RuntimeException())
// 即观察者接收后会直接调用onError（）

<-- never()  -->
// 该方法创建的被观察者对象发送事件的特点：不发送任何事件
Observable observable3=Observable.never();
// 即观察者接收后什么都不调用
```

##### 延时创建

* 需求场景

  1.定时操作：在经历了x秒后，需要自动执行y操作

  2.周期性操作：每隔x秒后，需要自动执行y操作

**defer()**

* 作用

  直到有观察者（Observer）订阅时，才动态创建被观察者对象（Observable）&发送事件

> 1. 通过 `Observable`工厂方法创建被观察者对象（`Observable`）
> 2. 每次订阅后，都会得到一个刚创建的最新的`Observable`对象，这可以确保`Observable`对象里的数据是最新的

* 应用场景

  动态创建被观察者对象（Observable）&获取最新的Obserable对象数据

**timer（）**

* 作用

  1.快速创建1个被观察者对象（Observable）

  2.发送事件的特点：延时指定时间后，发送1个数值0（Long类型）

> 本质=延迟指定事件后，调用一次`onNext()`

* 应用场景

  延时指定事件，发送一个0，一般用于检测

**interval()**

* 作用

  1.快速创建1个被观察者对象（Observable）

  2.发送事件的特点：每隔指定时间就发送事件

> 发送的事件序列=从0开始，无限递增1的整数序列

**intervalRange()**

* 作用

  1.快速创建1个被观察者对象（Observable）

  2.发送事件的特点：每隔指定时间就发送事件，可指定发送的数据的数量

> a.发送的事件序列=从0开始，无限递增1的整数序列
>
> b.作用类似于interval(),但可以指定发送数据的数量

**range()**

* 作用

  1.快速创建1个被观察者对象（`Observable`）

  2.发送事件的特点：连续发送1个事件序列，可指定范围

> a.发送的事件序列=从0开始，无限递增1的整数序列
>
> b.作用类似于intervalRange(),但区别在于：无延迟发送事件

**rangeLong()**

- 作用：类似于`range（）`，区别在于该方法支持数据类型 = `Long`
- 具体使用 
  与`range（）`类似，此处不作过多描述

![](http://upload-images.jianshu.io/upload_images/944365-d36c6ed319565acc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### 变换操作符:[地址](http://blog.csdn.net/carson_ho/article/details/78315437)

* 作用：对事件序列中的事件/整个事件序列进行加工处理(即变换)，使得其转变成不同的事件/整个事件序列

**Map()**

* 作用

  对被观察者发送的每1个事件都通过指定的函数处理，从而变换成另外一个事件

> 即，将被观察者发送的事件转换为任意的类型事件

* 应用场景

  数据类型转换

**FlatMap()**

* 作用：将被观察者发送的事件序列进行拆分&单独转换，再合并成一个新的事件序列，最后再进行发送

* 原理

  1.为事件序列中每隔事件都创建一个Observable对象

  2.将对每个原始事件转换后的新事件都存放到对应Observable对象

  3.将新建的每个Observable都合并到一个新建的，总的Observable对象

  4.新建的，总的Observable对象将新合并的事件序列发送给观察者Observer

* 应用场景

  无序的将被观察者发送的整个事件序列进行变换

  > 注：新合并生成的事件序列顺序是无序的，即 与旧序列发送事件的顺序无关

**ConcatMap()**

* 作用：类似FlatMap()操作符

* 与FlatMap（）的区别在于：拆分&重新合并生成的事件序列的顺序=被观察者旧序列生产的顺序

* 应用场景

  有序的将被观察者发送的整个事件序列进行变换

  > 注：新合并生成的事件序列顺序是有序的，即 严格按照旧序列发送事件的顺序

**Buffer()**

* 作用

  定期从被观察者（Observable）需要发送的事件中获取一定数量的事件&放到缓存区中，最终发送

* 应用场景

  缓存被观察者发送的事件

### 组合/合并操作符:[地址](https://blog.csdn.net/carson_ho/article/details/78455349)

##### 组合多个被观察者

**concat()/concatArray()**

* 作用

  组合多个被观察者一起发送数据，合并后按发送顺序串行执行

> 二者区别：组合被观察者的数量，即`concat()`组合被观察者数量<=4,而`concatArray()`则可>4

**merge()/mergeArray()**

* 作用

  组合多个被观察者一起发送数据，合并后按时间并行执行

> 1. 二者区别：组合被观察者的数量，即`merge（）`组合被观察者数量≤4个，而`mergeArray（）`则可＞4个
> 2. 区别上述`concat（）`操作符：同样是组合多个被观察者一起发送数据，但`concat（）`操作符合并后是按发送顺序串行执行

**concatDelayError()/mergeDelayError()**

* 作用

![](http://upload-images.jianshu.io/upload_images/944365-0a86e8e45f1abb6c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 合并多个事件

> 该类型的操作符主要是对多个被观察者中的事件进行合并处理

**zip()**

* 作用

  合并多个被观察者`Observable`发送的事件，生产一个新的事件序列(即组合后的事件序列)，并最终发送

> 1.事件组合方式=严格按照原先事件序列 进行对位合并
>
> 2.最终合并的事件数量=多个被观察者`Observable`中数量最少的数量

![](http://upload-images.jianshu.io/upload_images/944365-887b81d9bca4924a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**combineLatest()**

* 作用

  当两个`Observales`中的任何一个发送了数据后，将先发送了数据的Observables的最新一个数据与另外一个Observable发送的每个数据结合，最终基于该函数的结果发送数据

> 与`zip()`的区别：`zip()`按个数合并，即1对1合并；`COmbineLastest`按事件合并，即在同一时间上合并

**combineLatestDalayError()**

> 作用类似于`concatDelayError（）` / `mergeDelayError（）`

**reduce()**

* 作用

  把被观察者需要发送的事件聚合成1个事件&发送

> 聚合的逻辑根据需求撰写，但本质都是前2个数据聚合，然后与后1个数据继续进行聚合，依次类推

**collect()**

* 作用

  将被观察者`Observable`发送的数据事件收集到一个数据结构里

##### 发送事件钱追加发送事件

**startWith()/startWithArray()**

* 作用

  在一个被观察者发送事件前，追加发送一些数据/一个新的被观察者

> 追加数据顺序--先调用后追加

##### 统计发送事件数量

**count()**

* 作用

  统计被观察者发送事件的数量

### 功能性操作符

* 作用

  辅助被观察者`Observable`在发送事件时实现一些功能性需求

> 如错误处理、线程调度等

* 类型

![](http://upload-images.jianshu.io/upload_images/944365-ff3df2b42968833d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 连接被观察者&观察者

* 需求场景

  即使得被观察者&观察者形成订阅关系

* 对应操作符

**subscribe()**

* 作用

  订阅，即连接观察者&被观察者

##### 线程调度

* 需求场景

  快速、方便指定&控制被观察者&观察者的工作线程

##### 延时操作

* 需求场景

  即在被观察者发送事件前进行一些延时操作

* 对应操作符使用

**delay()**

* 作用

  使得被观察者延迟一段时间再发送事件

##### 在事件的生命周期中操作

* 需求场景

  在事件发送&接收的整个生命周期过程中进行操作

> 如发送事件前的初始化、发送事件后的回调请求等

* 对应操作符使用

**do()**

* 作用

  在某个事件的生命周期中调用

* 类型

  `do()`操作符有很多个，具体如下：

  ![](http://upload-images.jianshu.io/upload_images/944365-3f411ad304df78d5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#####  错误处理

* 需求场景

  发送事件过程中，遇到错误时的处理机制

* 对应操作符类型

  ![](http://upload-images.jianshu.io/upload_images/944365-abbc7ffe57770e84.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* 对应操作符使用

**onErrorReturn()**

* 作用

  遇到错误时，发送1个特殊事件&正常终止

> 可捕获在它之前发生的异常

**onErrorResumeNext()**

* 作用

  遇到错误时，发送1个新的`Observable`

> 1.onErrorResumeNext()拦截的错误=Throwable；若拦截Exception请用onEcxeptionResumeNext()
>
> 2.若onErrorResumeNext拦截的错误=Exception，则会将错误误传递给观察者的onError方法

**onExceptionResumeNext()**

* 作用 

  遇到错误时，发送1个新的`Observable`

> 1. `onExceptionResumeNext（）`拦截的错误 = `Exception`；若需拦截`Throwable`请用`onErrorResumeNext（）`
> 2. 若`onExceptionResumeNext（）`拦截的错误 = `Throwable`，则会将错误传递给观察者的`onError`方法

**retry()**

* 作用

  重试，即当出现错误时，让被观察者`Observable`重新发射数据

> 1. 接收到 onError（）时，重新订阅 & 发送事件
> 2. `Throwable` 和 `Exception`都可拦截

**retryUntil()**

* 作用

  出现错误后，判断是否需要重新发送数据

> 1. 若需要重新发送&持续遇到错误，则持续重试
> 2. 作用类似`retry(Predicate predicate)`

* 具体使用

  具体使用类似于`retry(Predicate predicate)`，唯一区别：返回true则不重新发送数据事件

**reteyWhen()**

* 作用

  遇到错误时，将发生的错误传递给一个新的被观察者`Observable`,并决定是否需要重新订阅原始被观察者`Observable`&发送事件

##### 重复发送

* 需求场景

  重复不断地发送被观察者事件

* 对应操作符类型

  repeat()&repeatWhen()

**rapeat()**

* 作用

  无条件地、重复发送被观察者事件

> 具备重载方法，可设置重复创建次数

**repeatWhen()**

* 作用

  有条件地、重复发送被观察者事件

* 原理

  将原始Observable停止发送事件的标识(Complete()/Error())转换成1个Object类型数据传递给一个新的被观察者（Observable），以此决定是否重新订阅&发送原来的Observable

> 1. 若新被观察者（`Observable`）返回1个`Complete` / `Error`事件，则不重新订阅 & 发送原来的 `Observable`
> 2. 若新被观察者（`Observable`）返回其余事件时，则重新订阅 & 发送原来的 `Observable`

### 过滤操作符:[地址](https://blog.csdn.net/carson_ho/article/details/78683064)

* 作用

  过滤/筛选被观察者`Observable`发送的事件&观察者`Observer`接收的事件

* 类型

  ![](http://upload-images.jianshu.io/upload_images/944365-83fb8e7038dfd51a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* 应用场景

  * 根据 指定条件 过滤事件
  * 根据 指定事件数量 过滤事件
  * 根据 指定时间 过滤事件
  * 根据 指定事件位置 过滤事件

#####  根据指定条件过滤事件

* 需求场景

  通过设置指定的过滤条件，当且仅当该事件满足条件，就将该事件过滤

* 对应操作符类型

  ![](http://upload-images.jianshu.io/upload_images/944365-ac41ab7e0cc5d2fd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* 对应操作符使用

**Filter()**

* 作用

  过滤特定条件的事件

**ofType()**

* 作用

  过滤指定数据类型的数据

**skip()/shipLat()**

* 作用

  跳过某个事件

**distinct()/distinctUntilChanged()**

* 作用

  过滤事件序列中的重复的事件/连续重复的事件

#####  根据指定事件数量过滤事件

* 需求场景

  通过设置指定的事件数量，仅发送特定数量的事件

* 对应操作符类型

  `take()`&`takeLast()`

* 对应操作符使用

**take（）**

* 作用

  指定观察者最多能接收到的事件数量

**takeLast()**

* 作用

  指定观察者只能接收到被观察者发送的最后几个事件

##### 根据指定时间过滤事件

* 需求场景

  通过设置指定时间,仅发送在该时间内的事件

* 对应操作符类型

  ![](http://upload-images.jianshu.io/upload_images/944365-e8287b741257a7f4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

* 对应操作符使用

**throttleFirst()/throttlrLast()**

* 作用

  在某段事件内,只发送该段时间内第一次/最后一次事件

* 原理示意图

  ![](http://upload-images.jianshu.io/upload_images/944365-1f42132c7350bd79.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**sample()**

* 作用

  在某段时间内,只发送该段时间内最新1次事件

> 与throllteLast()操作符类似

**throttleWithTimeout()/debounce()**

* 作用

  发送数据事件时,若2发送事件间隔<指定时间,就会丢弃前一次的数据,直到指定时间内都没有新的数据发射时才会发送最后一次的数据