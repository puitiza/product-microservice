#means that the image we are creating is based on a Java 17 OpenJDK image.
FROM openjdk:17
#ets the port number to connect to the host.
EXPOSE 8080
#allows us to copy a file into the docker image, in this case the application JAR file
COPY build/libs/*.jar app.jar
#Describes execution command to start a docker container, in this case execute the JAR to run a spring boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
