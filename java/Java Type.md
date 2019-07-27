### Type接口简介

> Type是所有类型的父接口

* 原始类型:  raw types对应Class
* 参数化类型:  parameterized types对应ParameterizedType
* 数组类型:  array types对应GenericArray Type
* 类型变量:  type variables对应TypeVariable
* 基本(原生)类型:  primitive 对应Class

> 子接口有ParameterizedType,TypeVariable,GenericArrayType,WildcardType,实现类有Class.

#### ParameterizedType参数化类型

* Type[]  getActualTypeArguments();
* Type  getRawType();
* Type getOwerType();

Type[] getActualTypeArguments(); 返回 这个 Type 类型的参数的实际类型数组。 如 Map<String,Person> map 这个 ParameterizedType 返回的是 String 类,Person 类的全限定类名的 Type Array。

Type getRawType(); 返回的是当前这个 ParameterizedType 的类型。 如 Map<String,Person> map 这个 ParameterizedType 返回的是 Map 类的全限定类名的 Type Array。

Type getOwnerType();返回的是这个 ParameterizedType 所在的类的 Type （注意当前的 ParameterizedType 必须属于所在类的 member）。解释起来有点别扭，还是直接用代码说明吧。 `比如 Map<String,Person> map 这个 ParameterizedType 的 getOwnerType() 为 null，而 Map.Entry<String, String>entry 的 getOwnerType() 为 Map 所属于的 Type。`

#### TypeVariable类型变量

* Type[] getBounds();得到上边界的Type数组.`如 K 的上边界数组是 InputStream 和 Serializable。 V 没有指定的话，上边界是 Object`
* D getGenericDeclaration();返回的是声明这个Type所在的类的Type
* String getName();返回的是这个type variable的名称

#### GenericArrayType

> 范型数组,组成数组的元素中有范型则实现了该接口; 它的组成元素是 ParameterizedType 或 TypeVariable 类型

#### WildcardType 通配符的类型

> {@code ?}, {@code ? extends Number}, or {@code ? super Integer} 这些类型 都属于 WildcardType,extends 用来指定上边界，`没有指定的话上边界默认是 Object， super 用来指定下边界，没有指定的话为 null。`

- Type[] getLowerBounds() 得到上边界 Type 的数组
- Type[] getUpperBounds() 得到下边界 Type 的数组