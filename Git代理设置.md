###git代理设置

* git config --global http.proxy http://127.0.0.1:1080 
* git config --global https.proxy https://127.0.0.1:1080

###git设置用户名密码

* git config –global http.proxy http://user:password@http://10.10.10.10:1080 

###git检验代理

* git config –get –global http.proxy

###git清除代理

* git config --global (或 --local) --unset http.proxy
* git config --global --unset http.proxy 
