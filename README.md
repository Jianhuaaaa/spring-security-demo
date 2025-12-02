# Spring Boot 3.5.3 结合 OAuth2 认证

## 测试 API

启动应用后，你可以通过以下方式测试 API：
Swagger UI：访问 http://localhost:8080/api/swagger-ui.html
H2 控制台：访问 http://localhost:8080/api/h2-console
API 端点：
GET: http://localhost:8080/api/users
GET: http://localhost:8080/api/users/{id}
POST: http://localhost:8080/api/users
PUT: http://localhost:8080/api/users/{id}
DELETE: http://localhost:8080/api/users/{id}

### H2 Console Login

```yaml
  # 数据源配置
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: sa
    password:
```

## 通过构造函数注入使用

使用提示：
需要确保Role实体类中的RoleName枚举正确定义
该 Repository 会被 Spring 自动扫描并创建实例
在 Service 中通过构造函数注入使用：

```java

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    // ...其他代码
}
```
