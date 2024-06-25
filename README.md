# 项目介绍

实现了留言的crud功能，并通过`is_delete`控制留言的撤回状态，提供了撤回和恢复留言的接口

## 项目架构

![image-20240625205500936](md_img\image-20240625205500936.png)

## 技术栈

- SpringBoot集合框架
- MyBatis

## 加分项

- 进行参数检查，并对可能发生的异常进行错误处理

## 数据库构建

```mysql
create table messages
(
    id          int auto_increment primary key,
    detail      varchar(512)                         not null,
    create_time timestamp  default CURRENT_TIMESTAMP not null,
    update_time timestamp  default CURRENT_TIMESTAMP not null,
    is_delete   tinyint(1) default 0                 not null
);
```

# 基础功能接口

## 查询所有留言

**请求路径：**

```http
GET /message
```

**请求头：**
无

**请求参数：**
无

**返回示例：**
成功返回包含着数据库中存储的所有留言的数组

## 查询单条留言

**请求路径：**

```http
GET /message/{id}
```

**请求头：**
无

**请求参数：**

| 名称 | 位置 | 类型   | 必选 | 说明   |
| ---- | ---- | ------ | ---- | ------ |
| id   | path | string | 是   | 留言id |

**返回示例：**

```json
{
    "id": 3,
    "detail": "111",
    "createTime": "2024-06-25T01:13:12",
    "updateTime": "2024-06-25T12:07:49",
    "delete": true
}
```

**错误处理**：

- 无法在数据库找到对应索引的信息
  - 返回状态码404

- 路径参数不为数字

  ```json
  {
      "error": "For input string: \"aaa\"",
      "status": "BAD_REQUEST"
  }
  ```

## 新增留言

**请求路径：**

```http
POST /message
```

**请求头：**
无

**请求参数：**

| 名称   | 位置 | 类型   | 必选 | 说明     |
| ------ | ---- | ------ | ---- | -------- |
| detail | body | string | 是   | 留言内容 |

**返回示例：**

```json
{
    "error": "留言成功",
    "status": "OK"
}
```

**错误处理**：

- 新增的留言为空字符串

  ```json
  {
      "error": "Message should not be empty",
      "status": "BAD_REQUEST"
  }
  ```

## 删除指定id留言

**请求路径：**

```http
DELETE /message/{id}
```

**请求头：**
无

**请求参数：**

| 名称 | 位置 | 类型   | 必选 | 说明   |
| ---- | ---- | ------ | ---- | ------ |
| id   | path | string | 是   | 留言id |

**返回示例：**

```json
{
    "error": "删除留言成功",
    "status": "OK"
}
```

**错误处理**：

- 无法在数据库找到对应索引的信息
  - 返回状态码404

- 路径参数不为数字

  ```json
  {
      "error": "For input string: \"aaa\"",
      "status": "BAD_REQUEST"
  }
  ```

## 更改留言信息

**请求路径：**

```http
PUT /message
```

**请求头：**
无

**请求参数：**

| 名称   | 位置 | 类型   | 必选 | 说明     |
| ------ | ---- | ------ | ---- | -------- |
| id     | body | int    | 是   | 留言id   |
| detail | body | string | 是   | 留言信息 |

**返回示例：**

```json
{
    "error": "修改留言成功",
    "status": "OK"
}
```

**错误处理**：

- 无法在数据库找到对应索引的留言
  - 返回状态码404

- 请求体中的id字段无法被解析为数字

  ```json
  {
      "error": "JSON parse error: Cannot deserialize value of type `java.lang.Integer` from String \"aaa\": not a valid `java.lang.Integer` value",
      "status": "BAD_REQUEST"
  }
  ```

- 更新的消息内容为空字符串

  ```json
  {
      "error": "Message should not be empty",
      "status": "BAD_REQUEST"
  }
  ```

## 撤回留言

**请求路径：**

```http
PUT /message/{id}
```

**请求头：**
无

**请求参数：**

| 名称 | 位置 | 类型   | 必选 | 说明   |
| ---- | ---- | ------ | ---- | ------ |
| id   | path | string | 是   | 留言id |

**返回示例：**

```json
{
    "error": "留言撤回成功",
    "status": "OK"
}
```

**错误处理**：

- 无法在数据库找到对应索引的信息
  - 返回状态码404

- 路径参数不为数字

  ```json
  {
      "error": "For input string: \"aaa\"",
      "status": "BAD_REQUEST"
  }
  ```

## 恢复撤回留言

**请求路径：**

```http
PUT /message/{id}
```

**请求头：**
无

**请求参数：**

| 名称 | 位置 | 类型   | 必选 | 说明   |
| ---- | ---- | ------ | ---- | ------ |
| id   | path | string | 是   | 留言id |

**返回示例：**

```json
{
    "error": "撤回留言恢复成功",
    "status": "OK"
}
```

**错误处理**：

- 无法在数据库找到对应索引的信息
  - 返回状态码404

- 路径参数不为数字

  ```json
  {
      "error": "For input string: \"aaa\"",
      "status": "BAD_REQUEST"
  }
  ```