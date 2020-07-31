# Linux服务器环境

## 前期准备

1. 下载Xshell ,https://www.netsarang.com/zh/free-for-home-school/ 下载免费版本
2. 下载postman
3. Git工具

## 配置信息
- 操作系统
  - Ubuntu Server 18.04.1 LTS 64位
- CPU
  - 1核
- 内存
  - 2GB
- 公网带宽
  - 1Mbps
## 器远程登录信息
- 公网ip地址
  - 81.70.27.208
- port
  - 22
- 用户名
  - ubuntu
- 密码
  - gH|4mF-%_]e3=
## 基本环境配置步骤
- 更新默认安装的软件包
  - `sudo apt-get upgrade`
- 搭建Node相关环境
  - 搭建Nodejs环境
    - `sudo apt-get install nodejs`
  - 安装Npm包管理器
    - `sudo apt-get install npm`
  - 安装CNpm包管理器
    - `npm install -g cnpm --registry=https://registry.npm.taobao.org`
- Python相关环境
  - Python2 & Python3 系统默认已安装
  - Python2版本PiP
    - `sudo apt-get install python-pip`
  - Python3版本PiP
    - `sudo apt-get install python3-pip`
- Mysql服务器
  - 安装Mysql服务器&客户端
    - `sudo apt-get install mysql-server mysql-client`
  - 配置Mysql服务器允许远程访问(MysqlWorkbench测试)
    - 修改mysql配置文件
      - `vim /etc/mysql/mysql.conf.d/mysqld.cnf`
      - `注释 以bind-address开头的行(就一行)`
      - `重启mysql服务器 sudo service mysql restart`
    - 修改root默认密码、用户允许访问的ip地址、加密方式(为了让mysqlworkbench能访问)
      - `use mysql`
      - `update user set authentication_string = password("password"), host = "%", plugin = 'mysql_native_password' where user = 'username';`
      - `flush privileges;`
    - Mysql服务器配置信息
      - ip地址
        - 81.70.27.208
      - 端口
        - 3306
      - 用户名
        - root
      - 密码
        - password
  - MysqlWorkbench测试

## 使用须知
- 安装软件时尽量做好记录
- 不要使用服务器做些不好的事情！
