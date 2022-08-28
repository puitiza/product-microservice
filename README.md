# product-microservice
This is a personal project in order to understand better microservices,
and it split by section and commits.

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

**H2 Browser console !** <a href="http://localhost:8080/h2-console" target="_blank" rel="noopener">http://localhost:8080/h2-console</a>

To Access is necessary to look in the application.yml file,
we have the url, username and password
```sh
spring:
  datasource:
  url: jdbc:h2:~/test
  username: sa
  password: password
``` 

| web  | intellij IDEA |
| --------  | -------- |
| <img src="https://user-images.githubusercontent.com/24264799/186257563-337dbf7c-1a04-4f4f-a150-1f8306f3b5df.png">      | <img src="https://user-images.githubusercontent.com/24264799/186260500-fad06882-3e09-4780-9e4c-f6ec7d7e028a.png"> |

  
> Note: Remember to start up this project with H2 is necessary initialize it
```sh
spring:
  jpa:
    hibernate:
      ddl-auto: create-drop
```


#  **JWT (JSON Web Token)**

JWT is popular for Authentication and Information Exchange. Server encodes data into a JSON Web Token and send it to the Client. The Client saves the JWT, then every Request from Client to protected routes or resources should be attached that JWT (commonly at header). The Server will validate that JWT and return the Response.

There are three important parts of a JWT: Header, Payload, Signature. Together they are combined to a standard structure: header.payload.signature.


<img src="https://bezkoder.com/wp-content/uploads/2019/10/spring-boot-authentication-jwt-spring-security-flow.png" width=40% height=40%>

Spring Security Authentication process: receive HTTP request, filter, authenticate, store Authentication data, generate token, get User details, authorize, handle exception…

## Sonarqube/PostgreSQL in Apple M1 Chip or Windows

### ‍💻 Installation

In your terminal

- Run `docker pull davealdon/sonarqube-with-docker-and-m1-macs`

  if you have on windonws OS:
  
  Run `docker pull sonarqube:9.6-community`

  In both case we are using version 9.x of sonarqube because this project is for Java 17
- Run `docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 davealdon/sonarqube-with-docker-and-m1-macs`
- Check in your navegator the addres <a href="http://localhost:9000" target="_blank" rel="noopener">http://localhost:9000</a>
- User and Pasword are "admin"

### ‍💻 Quickstart

- Is necesary to create a token and then configurate on your code inside `build.gradle` file
  <img width=50% height=50% src="https://user-images.githubusercontent.com/24264799/186265137-b8ce1208-af63-4387-8bd0-48dcc217c6c5.png">
  ```
  sonarqube {
      properties {
          property "sonar.host.url", "http://localhost:9000"
          property "sonar.login", "5f542370ca13ec8a9ca37048d7f32809c01681c0"
      }
  }
  ```
- Now you must to run a script for sonarqube in your terminal

   Run `./gradlew test jacocoTestReport`  This is to create coverage of your code and then
  
   Run `./gradlew sonarqube`  after this step you can see your project in sonarqube with sucessfull.
- The easiest way is to do it with gradle, only press in sonarqube like you can see below, but remember before build/clean.

|   |  |
| --------  | -------- |
|<img src="https://user-images.githubusercontent.com/24264799/186272202-d4affd86-0692-44ea-8a61-c47fd1bdb7ad.png">|<img src="https://user-images.githubusercontent.com/24264799/186266110-eae20b83-efae-4138-8a44-414beeaf8b6b.png">|

## Unit Test Junit5 + Mockito - Jacoco

### ‍💻 Installation

if you want to see how much is the coverage verified, follow these steps

1. Click in `clean project`
2. Click in `jacocoTestReport`
3. Click in `jacocoTestCoverageVerification`

|   |  |
| --------  | -------- |
|<img src="https://user-images.githubusercontent.com/24264799/187048754-7c5699cd-b4b0-47a6-9eb9-a8efd9ac7700.png">|<img src="https://user-images.githubusercontent.com/24264799/187048888-960727d6-4843-46df-9f00-fe675b784335.png">|



# wiki
- Sonarqube -> https://medium.com/@HoussemDellai/setup-sonarqube-in-a-docker-container-3c3908b624df
- Jacoco -> https://medium.com/codex/software-engineering-done-right-2358ae0d6dd4
- code Coverage (Sonarqube + Jacoco) -> https://tomgregory.com/how-to-measure-code-coverage-using-sonarqube-and-jacoco/
- Junit 5 + Mockito -> https://www.freecodecamp.org/news/unit-testing-services-endpoints-and-repositories-in-spring-boot-4b7d9dc2b772/

https://www.javaguides.net/2022/03/spring-boot-unit-testing-service-layer.html

https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockito-junit-example/

https://stackabuse.com/guide-to-unit-testing-spring-boot-rest-apis/

https://www.baeldung.com/spring-mvc-test-exceptions

# Contribution

- Report issues
- Open pull request with improvements
- Spread the word
- Reach out to me directly at <anthony.puitiza.02@gmail.com>
