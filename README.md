# product-microservice
This is a personal project in order to understand better microservices,
and it split by section and commits.

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)
[![Build Status](https://github.com/javiertuya/samples-test-spring/actions/workflows/build.yml/badge.svg)](https://github.com/javiertuya/samples-test-spring/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=my%3Asamples-test-spring&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=my%3Asamples-test-spring)
[![Javadoc](https://img.shields.io/badge/%20-javadoc-blue)](https://javiertuya.github.io/samples-test-spring/)

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


# **JWT (JSON Web Token)**

JWT is popular for Authentication and Information Exchange. Server encodes data into a JSON Web Token and send it to the Client. The Client saves the JWT, then every Request from Client to protected routes or resources should be attached that JWT (commonly at header). The Server will validate that JWT and return the Response.

There are three important parts of a JWT: Header, Payload, Signature. Together they are combined to a standard structure: header.payload.signature.


<img src="https://bezkoder.com/wp-content/uploads/2019/10/spring-boot-authentication-jwt-spring-security-flow.png" width=40% height=40%>

Spring Security Authentication process: receive HTTP request, filter, authenticate, store Authentication data, generate token, get User details, authorize, handle exception…

# Dockerfile

## ‍💻 Installation

In your terminal

- Run $ `docker build -t product-microservice .`

  Make sure to include **.** at the end
  
  Here, `-t` simply means tag followed by ‘ name:tag ’ format, for example: product-microservice:1.

  <img width=80% src="https://user-images.githubusercontent.com/24264799/188297522-97d1466e-06d1-4524-a402-b5e01c0498be.png">

  
- Run Docker container using the image built

  $ `docker run -d --name product-ms -p 8080:8080 product-microservice`

    `-d` means that we will start the container in a detached mode. It exits when the root process used to run the container exits.

    `-name` assigns the name of the container.

    `-p` exposes the container’s internal port. The format is -p hostPort:containerPort. The exposed container’s port can be directed through the specified host’s port. Thus, -p 8080:8080 binds the host’s 8080 port to the container’s internal 8080 port.

    `product-microservice` is the Image name along with the tag.

    <img width="1052" alt="image" src="https://user-images.githubusercontent.com/24264799/188297596-ab6524f1-ba5d-431e-8a79-aa57b5e3f9fa.png">

-  You can also have a look at the log file to see if my application ran successfully using 
    
    $ `docker logs -f product-ms`

    <img width=80%  src="https://user-images.githubusercontent.com/24264799/188303388-92c94916-9407-410f-832c-53dbd350c440.png">

-  You can see ip from some container with these 2 cmds

    $ `docker ps`
  
    $ `docker inspect <IdContainer> | grep "IPAddress"`
  
    <img width=80% src="https://user-images.githubusercontent.com/24264799/189508111-77c7c765-01d7-482f-963b-2c563e28ee6e.png">


### ‍Publish your image in Dockerhub (Optional)

1.  Run $ `docker login` and autenticathe with your credential, for password my suggestion is use token like in the image.

    <img width=60% src="https://user-images.githubusercontent.com/24264799/188303703-76bd749c-7af6-4038-ab30-3791ed5f0a0b.png">

    <img width=60% src="https://user-images.githubusercontent.com/24264799/188303677-eb40666b-8c46-446b-8fcb-f5b6145368e1.png">

2. tag your local docker image to remote docker image: 
   
```bash
docker tag product-microservice puitiza/product-microservice
```
   
3. Now run the command to push to remote docker image to DockerHub Repository: 
   
```bash
docker image push puitiza/product-microservice
```

<img width=60% src="https://user-images.githubusercontent.com/24264799/188303967-32e1cfa9-51ed-4bbf-a935-ae8a56124e76.png">

## Unit Test Junit5 + Mockito - Jacoco

## ‍💻 Installation

if you want to see how much is the coverage verified, follow these steps

1. Click in $ `clean project`
2. Click in $ `jacocoTestReport`
3. Click in $ `jacocoTestCoverageVerification`

|   |  |
| --------  | -------- |
|<img src="https://user-images.githubusercontent.com/24264799/187048754-7c5699cd-b4b0-47a6-9eb9-a8efd9ac7700.png">|<img src="https://user-images.githubusercontent.com/24264799/187048888-960727d6-4843-46df-9f00-fe675b784335.png">|

# Sonarqube/PostgreSQL in Apple M1 Chip or Windows

## ‍💻 Installation

In your terminal

- Run $ `docker pull davealdon/sonarqube-with-docker-and-m1-macs`

  if you have on windonws OS:
  
  Run $ `docker pull sonarqube:9.6-community`

  In both case we are using version 9.x of sonarqube because this project is for Java 17
- Run $ `docker run -d --name sonarqube -p 9000:9000 -p 9092:9092 davealdon/sonarqube-with-docker-and-m1-macs`
- Check in your navegator the addres <a href="http://localhost:9000" target="_blank" rel="noopener">http://localhost:9000</a>
- User and Pasword are "admin"

## ‍💻 Quickstart

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

   Run $ `./gradlew test jacocoTestReport`  This is to create coverage of your code and then
  
   Run $ `./gradlew sonarqube`  after this step you can see your project in sonarqube with sucessfull.
- The easiest way is to do it with gradle, only press in sonarqube like you can see below, but remember before build/clean.

|   |  |
| --------  | -------- |
|<img src="https://user-images.githubusercontent.com/24264799/186272202-d4affd86-0692-44ea-8a61-c47fd1bdb7ad.png">|<img src="https://user-images.githubusercontent.com/24264799/186266110-eae20b83-efae-4138-8a44-414beeaf8b6b.png">|

# Expose your services with NGROK

## ‍💻 Installation
  
- You can download Ngrok by exec or image on Docker. https://hub.docker.com/r/ngrok/ngrok

this is the easiest way to pull up ngrok service as image

```
 docker run --name ngrok_tmp --rm -it --net=host -e NGROK_AUTHTOKEN=<token_ngrok> ngrok/ngrok http 9000
```

`--rm` means that Remove the container what we created when this is stopped

`--net=host` means that we can use 'ngrok http <id_port>' example 'ngrok http 9000' otherwise we would have to use this form 
'ngrok http host.docker.internal:9000'
 
if you want to see thus the inspect of ngrok , so needs to expose the port by default, after run cmd you can see http://localhost:3000/inspect/http

```
docker run --name ngrok_tmp --rm -it -p 3000:4040 -e NGROK_AUTHTOKEN=<token_ngrok> ngrok/ngrok http host.docker.internal:9000
```

The purpose of this is to expose locally running sonarqube to the internet and then configure it on cd/ci.

<img width="1504" alt="image" src="https://user-images.githubusercontent.com/24264799/189812974-e9ad53cd-b315-40f3-ae26-a0596ef74cfe.png">
<img width="1588" alt="image" src="https://user-images.githubusercontent.com/24264799/189819886-f5752de6-cf32-4958-b4d8-25b65c101bd1.png">


# Deploy in Kubernetes

## ‍💻 Installation
  
1. Enable kubernetes on your docker desktop and after that you can use Lens Desktop for Ide

  |   |  |
  | --------  | -------- |
  |<img src="https://user-images.githubusercontent.com/24264799/189266942-af946b34-7220-45fe-a3f9-e41c10596cd8.png">|<img src="https://user-images.githubusercontent.com/24264799/189267884-98dd0d6e-c6ea-448e-ac58-eae1718501ef.png">|


  
2. Run in your terminal inside your project  $ `kubectl apply -f deployment.yaml` or you can do also by kubernetes plugin on Intellij Idea

  |   |  |
  | --------  | -------- |
  |<img src="https://user-images.githubusercontent.com/24264799/189163451-483aab36-644a-4739-a705-b6e93bf3358c.png">|<img src="https://user-images.githubusercontent.com/24264799/188344933-6e67f2c5-f967-4d62-a6d3-f89a188a9ed2.png">|

3. By default the metrics is not able in lens IDE with prometheus so you need to able it before. Run these commands in your terminal in this order

```
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
```
```
helm repo update
```
```
kubectl create namespace monitoring
```
```
helm install prometheus --namespace monitoring prometheus-community/kube-prometheus-stack --set nodeExporter.hostRootfs=false
```
In my case, The first time I ran only this cmd but Now I know that I have to add {--set ...}
```
helm install prometheus --namespace monitoring prometheus-community/kube-prometheus-stack
```
 If it doesn't work follow the steps I put here 
 <a href="https://github.com/prometheus-community/helm-charts/issues/467#issuecomment-1241426318" target="_blank" rel="noopener">node-exporter crashes at startup</a>

<img width=70% alt="image" src="https://user-images.githubusercontent.com/24264799/189272575-71cd6d5a-ed10-48b3-8f9e-040712a4d43f.png">


List of comands:
- $ `kubectl get nodes`       List of nodes in your cluster
- $ `kubectl get pods`        List of pods in your cluster
- $ `kubectl logs <podname>`  If you want to see logs

- $ `kubectl get svc`    List of services in your cluster
- $ `kubectl get namespaces <name>`   get the summary of a specific namespace using
- $ `kubectl port-forward -n monitoring service/prometheus-grafana 3000:80` Expose this service http://localhost:3000
- $ `kubectl get secret -n monitoring prometheus-grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo` 
Expose password : prom-operator, and for user: admin. Also you can see this secret with your Lens Desktop


# Contribution

- Report issues
- Open pull request with improvements
- Spread the word
- Reach out to me directly at <anthony.puitiza.02@gmail.com>

<details>
  <summary><b>🔗 wiki</b></summary>
  
  
**Sonarqube** 
- https://medium.com/@HoussemDellai/setup-sonarqube-in-a-docker-container-3c3908b624df

**Jacoco** 
- https://medium.com/codex/software-engineering-done-right-2358ae0d6dd4

**code Coverage (Sonarqube + Jacoco)** 
- https://tomgregory.com/how-to-measure-code-coverage-using-sonarqube-and-jacoco/

**Junit 5 + Mockito** 
- https://www.freecodecamp.org/news/unit-testing-services-endpoints-and-repositories-in-spring-boot-4b7d9dc2b772/
- https://www.javaguides.net/2022/03/spring-boot-unit-testing-service-layer.html
- https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockito-junit-example/
- https://stackabuse.com/guide-to-unit-testing-spring-boot-rest-apis/
- https://www.baeldung.com/spring-mvc-test-exceptions

**dockerfile** 
- https://medium.com/geekculture/docker-basics-and-easy-steps-to-dockerize-spring-boot-application-17608a65f657
- https://www.baeldung.com/dockerizing-spring-boot-application

**ngrok** 
- https://chriskirby.net/blog/using-ngrok-through-docker-for-local-service-development-on-mac
- https://tech.osteel.me/posts/docker-for-local-web-development-part-6-expose-a-local-container-to-the-internet

**Kubernetes**
- https://dzone.com/articles/spring-boot-with-kubernetes
- https://andrewlock.net/running-kubernetes-and-the-dashboard-with-docker-desktop/
- https://medium.com/@ahmedyosry963/kubernetes-in-your-local-environment-d63e62c3b5f
- https://medium.com/@dijin123/kubernetes-and-the-ui-dashboard-with-docker-desktop-5ad4799b3b61
- https://kubernetes.io/docs/tasks/administer-cluster/namespaces/
- https://www.youtube.com/watch?v=zW-E8THfvPY
  </details>
