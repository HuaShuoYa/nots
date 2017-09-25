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

