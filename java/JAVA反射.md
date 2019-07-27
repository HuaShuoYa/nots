> 反射:指程序可以访问,检查和修改它本身状态或行为的一种能力

| 方法关键字                                    |      含义      |
| ---------------------------------------- | :----------: |
| getDeclareMethods()                      |  获取类中所有的方法   |
| getReturnType()                          |  获取方法的返回值类型  |
| getParameterTypes()                      | 获取方法的传入参数类型  |
| getDeclareMethod("方法名,参数类型.class.......") |   获取指定的方法    |
| invoke(.Class)                           |     执行方法     |
| getAnnotations ()                        |  获取这个类中所有注解  |
| getClassLoader()                         | 获取加载这个类的类加载器 |
| isAnnotation()                           | 测试这类是否是一个注解类 |
| newInstance()                            |     无参实例     |
|                                          |              |
|                                          |              |
| 构造方法关键字                                  |      含义      |
| getDeclaredConstructors()                |  获取所有的构造方法   |
| getDeclaredConstructors(String name, Class… parameterTypes) |  获取指定的构造方法   |
|                                          |              |
| 成员变量                                     |      含义      |
| getDeclaredFields()                      |  获取所有的成员变量   |
| getDeclaredFields(参数类型.class......)      |  获取指定的成员变量   |
|                                          |              |
| 父类和父接口                                   |      含义      |
| getSuperclass()                          |   获取某类的父类    |
| getInterfaces()                          |  获取某类实现的接口   |

* Class类：代表一个类，位于java.lang包下
* Field类：代表类的成员变量（成员变量也称为类的属性）
* Method类：代表类的方法
* Constructor类：代表类的构造方法
* Array类：提供了动态创建数组，以及访问数组的元素的静态方法

> java获取class的三种方法

```java
//使用Class类的静态方法forName()，用类的名字获取一个Class实例
Class<?> bookClass = Class.forName("Book");

//利用对象调用getClass()方法获取该对象的Class实例
Book book = new Book();
Class<? extends Book> bookClass = book.getClass();

//运用.class的方式来获取Class实例，对于基本数据类型的封装类，还可以采用.TYPE来获取相对应的基本数据类型的Class实例
Class<Book> bookClass = Book.class;
Class<Integer> type = Integer.TYPE;
```

> 反射机制主要提供的功能

* 在运行时判断任意一个对象所属的类
* 在运行时构造任意一个类的对象
* 在运行时判断任意一个类所具有的成员变量和方法
* 在运行时调用任意一个对象的方法