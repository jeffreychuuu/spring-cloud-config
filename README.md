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
      name: root
      password: s3cr3t

```



Use **GET** api to check configuration /{applicationName}/{environment}[/{gitbranch}]

```sh
curl --location --request GET 'http://localhost:3301/spring-cloud-config-client/dev'
```



## Client

Static configuration require to initialize spring-cloud, we need to create a bootstrap.yml.

spring.application.name - is application which same as *{application}* in [repository](https://github.com/jeffreychuuu/spring-cloud-config-repo).

spring.cloud.config.profile -is same as profile in [repository](https://github.com/jeffreychuuu/spring-cloud-config-repo).

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

Use API to get the configuration

```sh
curl --location --request GET 'http://localhost:3302/whoami' 
```

Notify client to update configuration 

```sh
curl --location --request POST 'http://localhost:3302/actuator/refresh' 
```

