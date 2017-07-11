#### shadowsocks 服务器安装

```bash
sudo apt-get update			//更新软件源
sudo apt-get install python-pip		//然后安装 PIP 环境
sudo pip install shadowsocks		//安装 shadowsocks
```

#### 配置 shadowsocks

* 执行命令，编辑 shadowsocks.json 文件

```bash
vi /etc/shadowsocks.json
```

* 按i键进入编辑模式，将以下内容复制进去：

> 多用户配置

```json
{
    "server":"000.000.000.000",
    "local_address":"127.0.0.1",
    "local_port":1080,
    "port_password":{
        "8381":"king",
        "8382":"cat",
        "8383":"yinran",
        "8384":"other"
    },
    "timeout":300,
    "method":"aes-256-cfb",
    "fast_open":false
}
```

> 单用户配置

```json
{
    "server":"000.000.000.000",
    "server_port":8388,
    "local_address":"127.0.0.1",
    "local_port":1080,
    "password":"admin",
    "timeout":300,
    "method":"aes-256-cfb",
    "fast_open":false
}
```

* 注意： server 改成自己 VPS 的 IP 地址，端口号和密码视自己的需求修改，先在文本编辑器中修改完成再粘贴到 putty 窗口中。


* 粘贴完成后按 esc 键退出编辑模式，然后按 `:wq` ， 回车保存并退出。

| 字段          | 含义                                       |
| :---------- | :--------------------------------------- |
| server      | 服务器 IP (IPv4/IPv6)，注意这也将是服务端监听的 IP 地址    |
| server_port | 服务器端口                                    |
| local_port  | 本地端端口                                    |
| password    | 用来加密的密码                                  |
| timeout     | 超时时间（秒）                                  |
| method      | 加密方法，可选择 “bf-cfb”, “aes-256-cfb”, “des-cfb”, “rc4″, 等等。 |

#### 启动 shadowsocks

* 启动服务,执行命令

```bash
ssserver -c /etc/shadowsocks.json -d start
```

* 即可启动服务，至此 shadowsocks 多用户环境搭建完成。
* 如需终止服务，可执行以下命令：

```bash
ssserver -c /etc/shadowsocks.json -d stop
```

#### 额外的设置（可选）

##### 1.添加日志记录

* 执行以下命令：

```bash
vi /etc/supervisord.conf
```

* 同样的，按 i 进入编辑模式，将以下内容复制进去，保存退出。

```bash
[program:shadowsocks]
command=ssserver -c /etc/shadowsocks.json
autostart=true
autorestart=true
user=root
log_stderr=true
logfile=/var/log/shadowsocks.log
```

##### 2.设置开机启动

* 执行以下命令：

```
vi /etc/rc.local
```

* 进入编辑模式，将以下内容复制进去，保存退出。

```
service supervisord start
```

* 此时可以执行 `reboot` 命令重启系统，验证开机启动是否成功。
