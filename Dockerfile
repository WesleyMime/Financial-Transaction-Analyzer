#
# Build stage
#
FROM maven:latest AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

#
# Package stage
#
FROM openjdk:18-jdk-alpine
COPY --from=build /home/app/target/*.jar app.jar
VOLUME /main-app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-DMAIL_USERNAME=changeEmail","-DMAIL_PASSWORD=changePassword","-jar","/app.jar"]