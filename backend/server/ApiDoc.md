# Android端HTTP接口文档
## 接口文档说明
- pathhttp://81.70.27.208:8088/picture/1596198585.jpg
  - baseUrl是服务器的ip地址+服务器开启的端口号，文档中的path为路由路径。
  - 完整的请求路径格式为 `http://ip:port/path`
  - 举例子实现登录功能的请求地址为 `http://81.70.27.208:8000/api/login`
- method
  - 请求的方法举例子POST,GET,PUT,DELETE,OPTIONS等等
  - post
    - 一般情况下的Content-Type为`application/x-www-form-urlencoded`
    - 涉及到上传文件的接口Content-Type为`multipart/form-data`

- params说明
    - 加粗字体为必须参数，前端传的时候务必传过来，后端写代码的时候必须对这部分参数进行检查。
    - 不必须的参数，前端有需要的时候就传，不需要的时候，什么都不传(不能传null或者""这种数据过来)。
    - "**name**:string"
      - 加粗字体 必须传一个name参数
      - name参数是一个字符串
- 返回数据格式定义
  - 失败情况
    ```(json)
    {
        "status":0  //状态码 0表示失败 1表示成功
        "error_code":"2000" //错误码
        “error_des":"身份验证失败"  //错误描述
    }
    ```
  - 成功情况
    ```
    {
        "status":1 //状态码 0表示失败 1表示成功
        "data":{
            //返回的数据
        }
    }
    ```
- 错误码定义(明天再写)

    | 错误码 | 错误描述                                 |
    | :----: | :--------------------------------------- |
    |  1**   | 用户输入类错误                           |
    |  100   | 缺少必要的参数                           |
    |  101   | 传入参数格式有误                         |
    |  102   | 用户未登录                               |
    |  103   | 用户已登录                               |
    |  104   | 用户不存在                               |
    |  105   | 用户账号密码不匹配                       |
    |  112   | 资料类型输入有误                         |
	|  116	 | 用户未发布资料							  |
    |  120   | 找不到对应id的评论                       |
    |  124   | 关注用户未发布资料                        |
	|  125	 | 数据库无该类型的资料                      |
	|  126	 | 无关注的用户                             |
	|  127	 | 用户未选课                               |
    |  2**   | 数据库类错误                             |
    |  200   | 发起数据库请求出错                       |
    |  201   | 验证用户信息时出错                       |
    |  202   | 获取数据库连接出错                       |
    |  203   | 开启事务失败                             |
    |  204   | 提交事务失败                             |
    |  300   | 调用退出接口发生错误                       |
    |  500   | 出乎意料的错误                           |

## 接口列表
### 1.登录功能(已完成)
- function:未登录状态的用户可以通过账号密码进行登录，并在服务端设置session，保持用户登录态。
- path:/api/login
- method:post
- params
    - **userid**:string
    - **password**:string
- data
    ```
    data:{
        msg:'登录成功'
    }
	```
### 2.查询登录状态(已完成)
- function:调用该接口查看当前请求的cookie是否是已登录状态
- method:get
- path:/api/getlogin
- data
  ```
  //已登录
  data:{
      msg:"用户已登录",
      is_login:true
  }
  //未登录
  data:{
      msg:"用户未登录",
      is_login:false
  }
  ```
### 3.获取用户信息(已完成)
- function:调用该接口可获取用户的基本信息
- method:get
- path:/api/get_user_info
- params:
	-**op**：可选值 0 1
      - 0 表示需查询用户为自己，不需额外传入用户userid
      - 1 表示需查询用户为其他用户，需传入所查询用户userid
    -userid: string(查询的用户userid)
- data
  ```
  //获取成功
  data:{
      msg: "获取用户信息成功",
      user_info:[
            {
                name: varchar(45),
                email: varchar(45),
                sex:  varchar(45),
                school:  varchar(45),
                collage:  varchar(45),
                class:  varchar(45),
                wechat: varchar(45),
                phone:  varchar(45),
                picture:  varchar(255),
                year:smallint(10)
            }
        ]
  }
  //未登录
  data:{
      msg:"用户未登录"
  }
  ```
### 4.获取用户发布资料列表(已完成)
- function:调用该接口可获取用户所发布的资料列表
- method:get
- path:/api/get_user_publish
- params:
	-**op**：可选值 0 1
      - 0 表示需查询用户为自己，不需额外传入用户userid
      - 1 表示需查询用户为其他用户，需传入所查询用户userid
    -userid: string(查询的用户userid)
- data
  ```
  //获取成功
  data:{
      msg: "获取数据信息成功",
      data:[
            {
                dataid: varchar(45),
                if_article: tinyint(1),
                if_video:  tinyint(1),
                if_audio:  tinyint(1),
				type:varchar(10),
				name:varchar(45),
				imgUrl:varchar(255),
                title:  varchar(255),
                introduction:  text,
                picture: varchar(255),
                time:  datetime(6)
            }
        ]
  }
  ```
### 5.首页中获取部分资料用于展示(已完成)
- function:调用该接口可根据不同资料分类获取资料列表
	-控制数量：可输入目标最大获取数据条数，若不输入默认6条
	-类别：文章、视频、音频
- method:get
- path:/api/get_part_data
- params:
	-**op**：可选值 article video audio
      - article 表示查询部分文章列表
      - video 表示查询部分视频列表
      - audio 表示查询部分音频列表
    -max: int(想获取数据条数最大值，若不输入默认6条)
- data
  ```
  //获取成功
  data:{
      msg: "获取数据信息成功",
      data_info:[
            {
                dataid: varchar(45),
                title:  varchar(255),
                introduction:  text,
                picture: varchar(255),
                time:  datetime(6)
            }
        ]
  }
  ```
### 6.分类获取展示资料(已完成)
- function:调用该接口可根据分类获取不同资料列表
	- 分页展示：可根据输入的目标页数及单位数据条数分页显示数据
- method:get
- path:/api/get_data
- params:
	-**op**：可选值 article video audio
      - article 表示获取全部文章列表
      - video 表示获取全部视频列表
      - audio 表示获取全部音频列表
    -**page**: int(查询的页数，从1开始)
	-pcount: int(每一页的数据条数，可不输入，默认为10条)
- data
  ```
  //获取成功
  data:{
      msg: "获取数据成功",
      data_info:[
            {
                dataid: varchar(45),
                title:  varchar(255),
                introduction:  text,
                picture: varchar(255),
                time:  datetime(6)
            }，
			......
        ]
  }
  ```
### 7.获取资料的评价(已完成)
- function:根据传入的dataid获取该资料的全部评论
- method:get
- path:/api/get_comment
- params:
	-**dataid**：string(资料的dataid)
- data
  ```
  //获取成功
  data:{
      msg: "获取数据成功",
      comment_list:[
            {
                userid: varchar(45),
                name:  varchar(45),
                picture: varchar(255),
				commentid:varchar(45),
				comment:varchar(255),
                time:  datetime(6)
            }，
			......
        ]
  }
  ```
### 8.获取关注的人发布的资料(已完成)
- function:根据自身用户的userid获取所关注用户所发布的资料
	-按时间降序排列
- method:get
- path:/api/get_my_follow
- data
  ```
  //获取成功
  data:{
      msg: "获取数据成功",
      follow_user_data:[
            {
                userid: varchar(45),
				user_pic:varchar(255),
                name:  varchar(45),
				title: varchar(255),
				picture: varchar(255),
				introduction:text,
				dataid:varchar(45),
				time:  datetime(6),
                if_article:tinyint(1),
				if_video:tinyint(1),
                if_audio:tinyint(1),
            }，
			......
        ]
  }
  ```
### 9.对某条资料发布评价(已完成)
- function:根据自身用户的userid获取所关注用户所发布的资料
	-按时间降序排列
- method:post
- path:/api/send_comment
- params:
	-**dataid**：string(资料的dataid)
	-**comment**：string(评价内容)
- data
  ```
  //获取成功
  data:{
      "msg": "发布评论成功"
  }
  ```
### 10.退出登录功能(已完成)
- function:已登录用户可通过调用端口退出登录
- path:/api/loginout
- method:get
- data
    ```
    data:{
        msg:'退出成功'
    }
	```
### 11.查询并返回用户课表(已完成)
- function:根据自身用户的userid获取课程列表
- method:get
- path:/api/get_classtable
- data
  ```
  //获取成功
  data:{
      "msg": "发布评论成功",
	  "user_classtable":[
            {
                courseid: varchar(45),
				name:varchar(45),
                time:  varchar(45),
				place: varchar(45),
				number: varchar(45)
            }，
			......
        ]
  }
  ```
### 12.用户设置课表(已完成)
- function:设置用户课表（每次增一条）
- method:get
- path:/api/set_classtable
- params:
	-**name**：string(课程名称)
	-**time**：string(星期几)
	-**place**：string(上课地点)
	-**number**:string(第几节课)
- data
  ```
  //获取成功
  data:{
      "msg": "设置课表成功",
	  
  }
  ```
### 13.上传文章(已完成)
- function：上传文章及文章的封面图
- method:post
- path:/api/publish
- params:
	-**title**：string(文章标题)
	-**introduction**：string(简介)
	-**contend**：string(文章内容)
	-**photo**:file
- data
  ```
  //发布成功
  data:{
      "msg": "发布资料成功",
	  
  }
  ```
### 14.上传视频(已完成)
- function：上传视频及其介绍
- method:post
- path:/api/publish_video
- params:
	-**title**：string(文章标题)
	-**introduction**：string(简介)
	-**contend**：string(文章内容)
	-**audio**:file
- data
  ```
  //发布成功
  data:{
      "msg": "发布资料成功",
	  
  }
  ```
### 16.上传音频(已完成)
- function：上传音频及其介绍
- method:post
- path:/api/get_detail
- params:
	-**dataid**：string(资料dataid)
- data
  ```
  //发布成功
  data:{
	  "msg":"获取数据成功",
      "data":[
            {
                title: varchar(45),
				introduction:text,
				content:longtext,
                picture:  varchar(255),
				type: varchar(45),
				video: varchar(255),
				ausio:varchar(255)
            }，
			......
        ]
  }
  ```

