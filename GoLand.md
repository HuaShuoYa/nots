#### GoLand Set-cookie不生效跨域问题

```go
c.Header("Access-Control-Allow-Origin", "*")//对应的值不能为通配符“*”
c.Header("Access-Control-Allow-Credentials", "true")//该行代码表示允许跨域发送Cookie
```

> 参阅:[关于请求跨域及response中Set-Cookie无效问题记录](https://blog.csdn.net/a317560315/article/details/78397369)

