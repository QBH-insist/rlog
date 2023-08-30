# RLog-Framework

**一个优雅记录操作日志的框架，基于 Spring AOP +Aviator 实现**

参考 [如何优雅地记录操作日志？ - 美团技术团队](https://tech.meituan.com/2021/09/16/operational-logbook.html)



## 日志载体

### **@RLog 日志注解**

| **字段**   | **类型** | **说明**                 |
| -------- | ------ | ---------------------- |
| bizNo    | String | 业务标识                   |
| uniqueId | String | 业务对象唯一 ID（纯文本+可解析的表达式） |
| payload  | String | 日志载体（纯文本+可解析的表达式）      |

### **LogRecord.class 日志实体**

| **字段**         | **类型**    | **说明**                           |
|----------------|-----------|----------------------------------|
| uniqueId       | String    | 业务对象 ID（解析后）                     |
| operator       | Object    | 操作用户对象（OperatorAccessor Hook 获取） |
| bizNo          | String    | 业务标识，注解元数据获取                     |
| payload        | String    | 日志内容（解析后）                        |
| args           | Object[]  | 入参                               |
| returnValue    | Object    | 出参                               |
| Throwable      | Throwable | 方法发生的异常快照                        |
| executeSuccess | Integer   | 被切面的方法的执行结果,:1 成功 0 异常           |
| errorMsg       | String    | 被切面的方法的执行的异常信息                   |
| gmtCreated     | datetime  | 创建时间                             |

## rlog-spring-boot-starter

封装成了 starter ，使用者可以将其打包发布到私服，然后依赖引入使用，下面是使用教程

### 开启切面增强

配置文件

```YAML
rlog:  
    enable: true # false 不开启日志记录
```

### 方法维度添加注解 @RLog

#### **Payload 表达式语法：**

> 整体需要被双引号包裹

**字符模板**

- 使用单引号：

如：payload = "'我是日志'"

**变量占位（变量范围：方法的参数变量、方法内部自己定义的变量）**

使用 # 开头

- 单个变量

如：payload = "'我的年龄是' + #age"

- 变量的内部属性（需要提供 java bean 的 get 方法）

如：payload = "'我的名字是' + #user.name"

- 调用变量的方法：#method(Object)

如：payload = "'uri ：' + #getRequestURI(request)"

```Java

@RequestMapping("/ping")
@RLog(bizNo = "00010", payload = "' 名字:' + #user.name + ' 性别:' + #user.sex + ' age:' + #age  + ' uri:' + #getRequestURI(request)")
public String ping(HttpServletRequest request, User user) {
    # do somethings
    # 方法内部添加变量
    RLogVariableContext.putVariable("age", 18);


    return "pong";
}


```

### 日志落地 RLogSink 接口（必须）

此框架只负责记录操作日志，对于记录后的日志如果处理由开发者决定

实现 RLogSink 接口

```Java
eg.

@Slf4j
@Component
public class DefaultRLogSink implements RpLogSink {
    @Override
    public void sink(LogRecord logRecord) {
        log.info("log record:{}", logRecord);
    }
}

```

### 操作人信息 OperatorAccessor 接口（必须）

LogRecord 里面有 operator 属性，这里留一个 hook 钩子 让使用者负责提供

> 推荐方案：一般来说系统会有一个存储当前请求用户信息的线程上下文，实现这个接口，在里面把线程上下文的用户信息组装返回即可

```Java
eg.

@Component
public class OperatorAccessorImpl implements OperatorAccessor {

    @Override
    public Object getOperator() {
        User user = new User();
        user.setName("i am operator");

        return user;
    }
}
```

## 自定义函数 ParseFunction 抽象类（按需）

> 可参考aviatorscript 文档：https://www.yuque.com/boyan-avfmj/aviatorscript/xbdgg2

框架提供一些自定义函数解析度功能，继承 ParseFunction（继承于 AbstractFunction，参考 AbstractFunction 文档实现）

```Java
eg.

@Component
public class PingParseFunction extends ParseFunction {

    /**
     * ping(arg1)自定义函数只有一个参数，所以实现只有一个 AviatorObject 参数的方法
     * 如果有多个参数，实现相对应个数的方法即可
     */
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        
        // 通过 FunctionUtils 可以解析出不同类型的参数
        String ping = FunctionUtils.getStringValue(arg1, env);
        
        // 如果为空需要返回 AviatorNil.NIL，不能返回 null
        return new AviatorString("pong");
    }

    /**
     * 返回函数注册的名称
     */
    @Override
    public String getName() {
        return "ping";
    }
}

# 使用
payload = "'自定义函数测试' + #ping('这里可以是字符，也可以是变量')"


```

## 日志变量上下文 RLogVariableContext 类（按需）

有些场景方法参数变量不能满足日志载体表达式的解析，需要手动添加变量

```Java
eg.

@RLog(bizNo = "1111", payload = "'手动添加变量:' + #age")
public User getUser(User user) {
    RLogVariableContext.putVariable("age", 18);
    
    return User.EMPTY;
}

```

## 其他语法

参考：https://www.yuque.com/boyan-avfmj/aviatorscript/cpow90
