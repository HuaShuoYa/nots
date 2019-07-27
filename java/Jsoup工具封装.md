##  Android 爬虫,使用Jsoup解析Html像Json一样优雅

​	当我们做一些Android练手项目时,苦于无数据,这时候可以试试Jsoup爬虫,爬取任何网页上数据来丰富你App的内容;jsoup 是一款Java 的HTML解析器，可直接解析某个URL地址、HTML文本内容。它提供了一套非常省力的API，可通过DOM，CSS以及类似于jQuery的操作方法来取出和操作数据。使用起来也非常简单:

```java
String html = "<html><head><title>First parse</title></head>"
  + "<body><p>Parsed HTML into a doc.</p></body></html>";
Document doc = Jsoup.parse(html);
```

其解析器能够尽最大可能从你提供的HTML文档来创见一个干净的解析结果，无论HTML的格式是否完整。

但是用Jsoup选择某个节点有个问题,例如在很深的节点下用Jsoup选择代码如下:

```java 
doc.select("body div.nav.head-nav.avt.clearfloat li.cat-item.cat-item")
```

如果不熟悉JQuery选择器,请参考:[JQuery选择器](http://www.runoob.com/jquery/jquery-ref-selectors.html)

一条选择语句看着就很凌乱,如果多条的话,可想而知,看起来就很难看,而且发生错误很难纠错,所以就就Gson模仿解析Json的方式,制做一个工具,通过注解和反射去解析凌乱的选择器.Jsoup更多用法请参考[中文官网](http://www.open-open.com/jsoup/)

​	首先定义一个注解类:

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Pick {
    String value() default "";

    String attr() default Attrs.TEXT;
}
```

对注解知识不熟悉的情参考[Java Annotation 及几个常用开源项目注解原理简析](http://www.trinea.cn/android/java-annotation-android-open-source-analysis/)

注解在RUNTIME时执行,作用对象为成员变量和类,通过`@Pick()`去注解实体类

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
</head>
<body>
    <div class="nav head-nav avt clearfloat">
        <li class="on"><a href=/>首页</a>
        </li>
        <li class="cat-item cat-item-1 "><a href="/movie/" title="最新电影">最新电影</a></li>
        <li class="cat-item cat-item-2 "><a href="/television/" title="最新电视">最新电视</a></li>
        <li class="cat-item cat-item-3 "><a href="/dongman/" title="动漫动画">动漫动画</a></li>
        <li class="cat-item cat-item-4 "><a href="/video/" title="综艺娱乐">综艺娱乐</a></li>
        <li class="cat-item cat-item-5 "><a href="/movie/dldy/" title="大陆电影">大陆电影</a></li>
        <li class="cat-item cat-item-6 "><a href="/movie/gtdy/" title="港台电影">港台电影</a></li>
        <li class="cat-item cat-item-7 "><a href="/movie/rhdy/" title="日韩电影">日韩电影</a></li>
        <li class="cat-item cat-item-8 "><a href="/movie/omdy/" title="欧美电影">欧美电影</a></li>
        <li class="cat-item cat-item-9 "><a href="/television/dljj/" title="大陆剧集">大陆剧集</a></li>
        <li class="cat-item cat-item-10 "><a href="/television/gtjj/" title="港台剧集">港台剧集</a></li>
        <li class="cat-item cat-item-11 "><a href="/television/rhjj/" title="日韩剧集">日韩剧集</a></li>
        <li class="cat-item cat-item-12 "><a href="/television/omjj/" title="欧美剧集">欧美剧集</a></li>
    </div>
</body>
</html>
```

当我们在解析这样一个Html片段时,我们需要的内容有href以及title的一个List,那我们就可以编写实体类了

```java
@Pick("body")
public class Entity {

    @Pick("div.nav.head-nav.avt.clearfloat li.cat-item.cat-item")
    private List<SortEntity> sortList=new ArrayList<>();

    public static class SortEntity {
        @Pick(value = "a")
        private String name = "";
        @Pick(value = "a", attr = Attrs.HREF)
        private String linkUrl = "";

        public SortEntity() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }
    }
}
```

需要注意的一点是:如果Entity是内部类,那么Entity一定要用public static修饰

实现思路:通过`body`注解类Entity,就相当于用`doc.select("body")` ,然后用其返回值,一层层的向下解析,直到完全解析,解析思路有了,那么就开始编写解析类去通过反射与注解来解析Entity类,实现过程如下:

```java
public class JsoupUtils {
	//传入待解析的字符串与编写好的实体类
    public <T> T fromHTML(String html, Class<T> clazz) {
        T t = null;
        Pick pickClazz;
        try {
            //先用Jsoup实例化待解析的字符串
            Document rootDocument = Jsoup.parse(html);
            //获取实体类的的注解
            pickClazz = clazz.getAnnotation(Pick.class);
            //构建一个实体类的无参构造方法并生成实例
            t = clazz.getConstructor().newInstance();
            //获取注解的一些参数
            String clazzAttr = pickClazz.attr();
            String clazzValue = pickClazz.value();
            //用Jsoup选择到待解析的节点
            Element rootNode = getRootNode(rootDocument, clazzValue);
            //获取实体类的所有成员变量
            Field[] fields = clazz.getDeclaredFields();
            //遍历并解析这些成员变量
            for (Field field : fields) {
                dealFieldType(field, rootNode, t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    private Field dealFieldType(Field field, Element rootNode, Object t) throws Exception {
        //设置成员变量为可修改的
        field.setAccessible(true);
        Pick pickField = field.getAnnotation(Pick.class);
        if (pickField == null) return null;
        String fieldValue = pickField.value();
        String fieldAttr = pickField.attr();
        //获取field的类型
        Class<?> type = field.getType();
        //目前此工具类只能解析两种类型的成员变量,一种是String的,另一种是带泛型参数的List,泛型参数必须是自定义
        //子实体类,或者String,自定义子实体类如果是内部类,必须用public static修饰
        if (type == String.class) {
            String nodeValue = getStringNode(rootNode, fieldAttr, fieldValue);
            Filter filterField = field.getAnnotation(Filter.class);
            if (filterField != null) {
                String filter = filterField.filter();
                boolean isFilter = filterField.isFilter();
                boolean isMatcher = RegexUtils.getRegexBoolean(nodeValue, filter);
                if (isFilter && isMatcher) {
                    field.set(t, nodeValue);
                } else {
                    return null;
                }
            } else {
                field.set(t, nodeValue);
            }
        } else if (type == List.class) {
            Elements elements = getListNode(rootNode, fieldValue);
            field.set(t, new ArrayList<>());
            List<Object> fieldList = (List<Object>) field.get(t);
            for (Element ele : elements) {
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    Type[] args = ((ParameterizedType) genericType).getActualTypeArguments();
                    Class<?> aClass = Class.forName(((Class) args[0]).getName());
                    Object object = aClass.newInstance();
                    Field[] childFields = aClass.getDeclaredFields();
                    for (Field childField : childFields) {
                        dealFieldType(childField, ele, object);
                    }
                    fieldList.add(object);
                }
            }
            field.set(t, fieldList);
        }
        return field;
    }

    /**
     * 获取一个Elements对象
     */
    private Elements getListNode(Element rootNode, String fieldValue) {
        return rootNode.select(fieldValue);
    }

    /**
     * 获取返回值为String的节点
     * 
     * 由于Jsoup不支持JQuery的一些语法结构,例如  :first  :last,所以这里手动处理了下,自己可参考JQuery选择器
     * 扩展其功能
     */
    private String getStringNode(Element rootNode, String fieldAttr, String fieldValue) {
        if (fieldValue.contains(":first")) {
            fieldValue = fieldValue.replace(":first", "");
            if (Attrs.TEXT.equals(fieldAttr))
                return rootNode.select(fieldValue).first().text();
            return rootNode.select(fieldValue).first().attr(fieldAttr);
        } else if (fieldValue.contains(":last")) {
            fieldValue = fieldValue.replace(":last", "");
            if (Attrs.TEXT.equals(fieldAttr))
                return rootNode.select(fieldValue).last().text();
            return rootNode.select(fieldValue).last().attr(fieldAttr);
        } else {
            if (Attrs.TEXT.equals(fieldAttr))
                return rootNode.select(fieldValue).text();
            return rootNode.select(fieldValue).attr(fieldAttr);
        }
    }

    /**
     * 获取根节点,通常在类的注解上使用
     */
    private Element getRootNode(Document rootDocument, String value) {
        return rootDocument.selectFirst(value);
    }
}
```

在JsoupUtils已经将所有的解析工作都已经完成,重要实现步骤在上面都用注释

我们使用的方法就是`Entity entity=new JsoupUtils().fromHtml(htmlStr,Entity.class)`是不是很熟悉,类似于Gson解析Json的写法,用户只需要编写实体类,然后就会自动对实体的各项进行赋值

#### 结合Retrofit使用

现在网络请求大多数都是使用Retrofit2+Rxjava2那么有没有办法像`GsonConverterFactory.create()`直接将返回的数据转换为实体类,答案是有的,通过自定义Converter

```java
public class HtmlConverterFactory extends Converter.Factory {

    private JsoupUtils mPicker;

    public static HtmlConverterFactory create(JsoupUtils fruit) {
        return new HtmlConverterFactory(fruit);
    }

    public static HtmlConverterFactory create() {
        return new HtmlConverterFactory(new JsoupUtils());
    }

    private HtmlConverterFactory(JsoupUtils fruit) {
        mPicker = fruit;
    }

    //我们只需要对返回值做修改,所以仅重写responseBodyConverter方法
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(
            Type type, Annotation[] annotations, Retrofit retrofit) {
        return new HtmlResponseBodyConverter<>(mPicker, type);
    }
}
```

自定义HtmlConverterFactory类继承Converter.Factory,并且在`create()` 里创建JsoupUtils实例,我们只需要对返回值做修改,所以仅重写responseBodyConverter方法,并且创建HtmlResponseBodyConverter<T>类实现Converter<ResponseBody, T>接口

```java
public class HtmlResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private JsoupUtils mPicker;
    private Type mType;

    HtmlResponseBodyConverter(JsoupUtils fruit, Type type) {
        mPicker = fruit;
        mType = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String response = value.string();
            return mPicker.fromHTML(response, (Class<T>) mType);
        } finally {
            value.close();
        }
    }
}
```

在covert方法中把获取ResponseBody.string(),然后调用mPicker.fromHTML(response, (Class<T>) mType),这样就可以在自动对实体类进行赋值了,Rxjava+Retrofit+JsoupUtils的请求代码示例如下:

```java
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RequestUrl.MAIN_URL)
                .addConverterFactory(HtmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
retrofit.create(ServerApi.class)
    			.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Entity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        
                    }

                    @Override
                    public void onNext(DetailListInfo info) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

```

封装了这个JsoupUtils工具简化代码,同时学习了java知识注解与反射,

