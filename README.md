# product-microservice
This is a personal project in order to understand better microservices,
and it split by section and commits.

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

**H2 Browser console !**
```sh
http://localhost:8080/h2-console
```

To Access is necessary to look in the application.yml file,
we have the url, username and password
```sh
spring:
  datasource:
  url: jdbc:h2:~/test
  username: sa
  password: password
```
![img_1.png](src/main/resources/images/img_1.png)

  
> Note: Remember to start up this project with H2 is necessary initialize it
```sh
spring:
  jpa:
    hibernate:
      ddl-auto: create-drop
```