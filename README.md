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

## Spring Security Dependencies

```markdown
<!-- Start of Spring Security Dependencies -->

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <!-- Spring Security OAuth2 Resource Server (for JWT) -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
        </dependency>
        <!-- JWT Support -->
        <dependency>
            <groupId>com.nimbusds</groupId>
            <artifactId>nimbus-jose-jwt</artifactId>
            <version>10.5</version>
        </dependency>
        <!-- 密码加密 -->
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-crypto</artifactId>
        </dependency>
        <!-- JJWT 核心API（必须） -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>0.12.6</version>
        </dependency>
        <!-- JJWT 实现（运行时依赖） -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>0.12.6</version>
            <scope>runtime</scope>
        </dependency>
        <!-- JJWT JSON 序列化（Jackson） -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>0.12.6</version>
            <scope>runtime</scope>
        </dependency>
        <!-- End of Spring Security Dependencies -->
```