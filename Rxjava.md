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

​	1.快速创建被观察对象（Observable）&发送10个以上事件（数组形式）

​	2.数组元素遍历

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