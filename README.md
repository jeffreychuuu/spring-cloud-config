# spring-cloud-config

Spring cloud configuration

## Configuration Repository

[spring-cloud-config-repo](https://github.com/jeffreychuuu/spring-cloud-config-repo)

Configuration in this repository can follow these patterns:

```sh
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```

*{label}* placeholder refers to a Git branch,

*{application}* to the client's application name 

*{profile}* to the client's current active application profile

## Server

Server-side need to provide a configuration repository. Also, spring-security password requires to protect the endpoint access from the non-authorized client.

Here I use Basic Auth with `Username: root` & `Password: s3cr3t`

Machanism of Basic Auth: `base64(${username}:${password})`

```yml
server:
  port: 3301
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/jeffreychuuu/spring-cloud-config-repo.git
  security: 
    user:
      name: root # use in Baisc Auth
      password: s3cr3t # use in Baisc Auth
```

Use **GET** api to check configuration /{applicationName}/{environment}[/{gitbranch}]

```sh
curl --location --request GET 'http://localhost:3301/spring-cloud-config-client/dev' \
--header 'Authorization: Basic cm9vdDpzM2NyM3Q='
```

## Client

Static configuration require to initialize spring-cloud, we need to create a `bootstrap.yml`.

spring.application.name - is application which same as *{application}* in [repository](https://github.com/jeffreychuuu/spring-cloud-config-repo).

spring.cloud.config.profile - is same as profile in [repository](https://github.com/jeffreychuuu/spring-cloud-config-repo).

management.endpoints.web.exposure - We need to ensure the spring actuator not expose too much sensitive record.

```yaml
server:
  port: 3302
spring:
  application:
    name: spring-cloud-config-client
  cloud:
    config:
      enable: true
      uri: http://localhost:3301
      username: root
      password: "s3cr3t"
      profile: dev
      #failFast: true
management:
  endpoints:
    web:
      exposure:
        include: health,refresh
        exclude: env
```

Adding `@RefreshScope` allow for beans to be refreshed dynamically at runtime.

Then the paramter with @Value annotation is allowed to update dynamically at runtime

```java
@RestController
@RefreshScope
public class GitController {
    @Autowired
    ConfigurationProperty configurationProperty;

    @Value("${application.user.name}")
    private String name;

    @GetMapping(value = "/whoami")
    public ConfigurationProperty.User whoami() {
        return configurationProperty.getUser();
    }

    @GetMapping(value = "/name")
    public String getName() {
        return name;
    }
}
```

The value in configuartion server will automatically match with the POJO

```java
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "application")
public class ConfigurationProperty {
    User user;

    public static class User{
        String name;
        String role;
        String tribe;
        ArrayList<String> group = new ArrayList<>();
        Map<String, ArrayList> departmentRoles = new HashMap<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getTribe() {
            return tribe;
        }

        public void setTribe(String tribe) {
            this.tribe = tribe;
        }

        public ArrayList<String> getGroup() {
            return group;
        }

        public void setGroup(ArrayList<String> group) {
            this.group = group;
        }

        public Map<String, ArrayList> getDepartmentRoles() {
            return departmentRoles;
        }

        public void setDepartmentRoles(Map<String, ArrayList> departmentRoles) {
            this.departmentRoles = departmentRoles;
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
```

Use API to get the user

```sh
curl --location --request GET 'http://localhost:3302/whoami' 
```

Use API to get the username value

```sh
curl --location --request GET 'http://localhost:3302/name' 
```

Notify client to use the latest configuration value

```sh
curl --location --request POST 'http://localhost:3302/actuator/refresh' 
```
