* 打包Linux可执行文件

  >```bash
  >set GOARCH=amd64
  >
  >set GOOS=linux
  >
  >go build main.go
  >```
  >
  >会生成一个没有后缀的xx二进制文件
  >
  >将该文件放入linux系统某个文件夹下
  >
  >赋予权限
  >
  >`chmod 777 main`
  >
  >执行
  >
  >./main
  >
  >

* golang跨域问题

  > https://blog.csdn.net/u010918487/article/details/82686293

