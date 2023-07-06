# 鲜花商城SSM

#### 介绍
这是之前servlet版本升级后的ssm版本,并对其中几个逻辑功能进行修复和加强

Flow_shop_project

主要是对一个基本电商项目的练手项目,里面的一些思想比较好,很适合刚学完servlet的小伙伴去巩固基础,并作出一个相对有成就感的项目.

项目技术栈：SSM+ JSP + MySQL + BootStrap + AJAX + JSON

项目使用工具：IDEA + Tomcat + SQLyog + Maven + Git

项目开发模式：采用团队开发方式，通过git最后进行项目整合并推送到gitee/github上
项目功能 ：

1. 本项目包含前台和后台两个方向
2. 前台功能包含(无需登录)：首页数据展示（包括分类展示、推荐产品、分页展示）、用户登录（包含用户异步验证）、用户注册、商品详情展示、加入购物车、模糊查询
3. 前台功能包含(需要登录)：订单提交、我的订单查看、用户注销、留言功能
4. 后台功能：管理员登录、注销、配送地区管理、用户管理（分为普通用户和管理员用户）、蛋糕商品管理（图片上传）


部署步骤:

1. idea中clone下来,可能要配置一下setting中的Maven地址
2. resources下面在图中所示文件里修改数据库信息，然后在SQLyog或者Navicat中创建一个数据库导入sql
![输入图片说明](https://foruda.gitee.com/images/1688632722025391068/4b4afb0b_12492075.png "1.png")
- 数据库创建选择：
![输入图片说明](https://foruda.gitee.com/images/1688632750566889772/40aee562_12492075.png "2.png")
3. 配置tomcat，然后启动下面会遇到一些问题
    1）:Json使用的是jsonlib比较老，这里是在webapp下面的lib里导入的
![输入图片说明](https://foruda.gitee.com/images/1688632806750176469/9daff451_12492075.png "3.png")

