# bookstore

1. 用户管理：管理员身份登录后，可以看到该功能；用户分为两种角色：顾客和管理员 								
   - 管理员可以禁用/解禁用户，被禁用的用户将无法登录系统								
2. 用户登录与注册 								
   - 用户登录需要输入用户名和密码，未输入点登录按钮时，提示用户必须输入								
   - 被禁用用户无法登录系统，并且会提示用户“您的账号已经被禁用”								
   - 根据用户名来确认其为管理员还是顾客，不同角色的界面具有差异								
   - 新用户注册时需要填写用户名、密码、重复密码、邮箱								
   - 需要校验用户名是否重复、两次输入的密码是否相同、邮箱是否符合格式要求								
3. 书籍管理：管理员身份登录后，可以看到该功能								
   - 管理员可以浏览数据库中已有的书籍，以列表形式显示，包括书名、作者、封面、ISBN编号和库存量								
   - 在列表上方提供搜索功能，管理员可以用书名来过滤想要查找的书籍								
   - 管理员在列表中可以修改每本图书的上述各种属性，包括书名、作者、封面、ISBN编号和库存量								
   - 管理员可以删除旧图书，可以添加新图书								
4. 浏览书籍：此部分功能可以复用“书籍管理”中的功能								
   - 顾客和管理员都可以浏览数据库中已有的书籍，以列表形式显示，包括书名、作者、封面、ISBN编号和库存量								
   - 提供搜索功能，用户可以用书名来过滤想要查找的书籍								
   - 选中某本书后，通过Ajax方式获取并显示书的详细信息								
5. 购买书籍								
   - 当用户浏览书籍时，可以选择将某本书放入购物车								
用户可以浏览购物车，看到自己放入购物车的所有书籍								
   - 在购物车中点击购买书籍之后，清空购物车，同时书籍库存相应地减少								
   - 购买书籍后，生成订单，展示给用户，并将订单存入数据库								
6. 订单管理								
   - 顾客可以查看自己的所有订单，并且可以使用搜索功能来实现过滤，具体可以按照时间范围或书籍名称过滤								
   - 管理员可以查看系统中所有的订单，并且可以使用搜索功能来实现过滤，具体可以按照时间范围或书籍名称过滤								
7. 统计								
   - 管理员可以统计在指定时间范围内各种书的销量情况，按照销售量排序，形成热销榜，以图或表的方式呈现								
   - 管理员可以统计在指定时间范围内每个用户的累计消费情况，按照购书进行排序，形成消费榜，以图或表的方式呈现								

## 应用系统体系架构
1. @SessionScope 登录时长显示
2. @Transactional 事务传播与隔离属性
3. kafka消息中间件 订单异步处理
   - `D:\apache-zookeeper-3.8.0-bin\bin\zkServer.cmd`
      如果端口号被占用 可以通过`netstat -ano`、`taskkill /pid x /f`、`tasklist`解决
   - `D:\kafka_2.13-3.2.3\bin\windows\Kafka-server-start.bat D:\kafka_2.13-3.2.3\config\server.properties`
     `D:\kafka_2.13-3.2.3\log` 要先删干净否则会报错
4. websocket推送订单处理结果
5. 本地生成证书 springboot配置后可Https访问 (需要8080重定向到8443且浏览器信任此证书)
   浏览器空白处输入thisisunsafe可去除警告
   前端无法使用重定向前的地址 只能手动改成https和8443
