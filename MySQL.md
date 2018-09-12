#### MySQL常用命令

* **show databases;**显示所有数据库
* **show tables;**显示数据库中的数据表


* **create database `DBName`;**创建数据库

* **use `DBName`;**选中数据库

* 创建表：

  ```mysql
  create table TableName(
      id int(10) unsigned zerofill not null auto_increment,
      email varchar(40) not null,
      ip varchar(15) not null,
      state int(10) not null default '-1',
  primary key (id)
  )engine=InnoDB;
  ```

* **drop table `TableName`;**删除表

* **alter table `oldName` rename `newName`;**重命名表

* **alter table `TableName` add `column` int(4) not null;**添加列

* **alter table `TableName` change `old`  `new` varchar(10)  not null;**修改列

* **alter table `TableName` drop `column`;**删除列

* **insert into `TableName` (id,email,ip,state) values(1,"qq@qq.com","127.0.0.1","0");**插入数据

* **delete from  `TableName` where `id=1`;**删除数据

* **update `TableName` set `id='2'`,`email='svip@qq.com'` where `id=1`;**修改数据

* 查数据

  ```mysql
  select * from TableName;  #取所有数据
  select * from TableName limit 0,2;  #取前两条数据 
  select * from TableName email like '%qq%' #查含有qq字符 _表示一个 %表示多个
  select * from TableName order by id asc;#降序desc
  select * from TableName id not in('2','3');#id不含2,3或者去掉not表示含有
  select * from TableName timer between 1 and 10;#数据在1,10之间
  ```

* 清空表中所有内容

  **delete from `TableName`**

  **truncate table `TableName`**

  >
  > 不带where参数的delete语句可以删除mysql表中所有内容，使用truncate table也可以清空mysql表中所有内容。效率上truncate比delete快，但truncate删除后不记录mysql日志，不可以恢复数据。
  >
  > delete的效果有点像将mysql表中所有记录一条一条删除到删完，而truncate相当于保留mysql表的结构，重新创建了这个表，所有的状态都相当于新表。

* 修改字段排列位置

  **alter table `TableName` modify `column1`varchar(20)  FIRST|AFTER `column2`**

  > FIRST，可选参数 
  > 将字段1，修改为表的第一个字段。 
  > AFTER 字段名2 
  > 将字段1，插入到字段2的后面。 

#### MySQL基本操作

* 登录

  ```mysql
  [root@host]# mysql -u root -p
  Enter password:*******
  ```


