FROM openjdk:18-jdk-alpine
VOLUME /main-app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-DMAIL_USERNAME=changeEmail","-DMAIL_PASSWORD=changePassword","-DEUREKA_URI=http://eureka-server:8761","-jar","/app.jar"]