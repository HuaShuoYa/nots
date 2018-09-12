### Json输出toString()

```java
public java.lang.String toString() {  
#if ( $members.size() > 0 )  
#set ( $i = 0 )  
return "{\"$classname\":{"  
#foreach( $member in $members )  
#if ( $i == 0 )  
+ "##  
#else  
+ ", ##  
#end  
#if ( $member.array )  
\"$member.name\":" + java.util.Arrays.toString($member.accessor)  
#elseif ( $member.string || $member.primitive || $member.numeric || $member.boolean || $member.enum )  
\"$member.name\":\"" + $member.accessor + "\""  
#else  
\"$member.name\":" + $member.accessor  
#end  
#set ( $i = $i + 1 )  
#end  
+ "}}";  
#else  
return "{$classname}";  
#end  
} 
```

### Getter不为null

```java
#if($field.modifierStatic)
static ##
#end
$field.type ##
#set($name = $StringUtil.capitalizeWithJavaBeanConvention($StringUtil.sanitizeJavaIdentifier($helper.getPropertyName($field, $project))))
#if ($field.boolean && $field.primitive)
  #if ($StringUtil.startsWithIgnoreCase($name, 'is'))
    #set($name = $StringUtil.decapitalize($name))
  #else
    is##
#end
#else
  get##
#end
${name}() {
  #if ($field.string)
     return $field.name == null ? "" : $field.name;
  #else 
    #if ($field.list)
    if ($field.name == null) {
        return new ArrayList<>();
    }
    return $field.name;
    #else 
    return $field.name;
    #end
  #end
}
```

