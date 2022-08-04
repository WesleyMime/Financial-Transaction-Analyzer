FROM openjdk:18-jdk-alpine
VOLUME /main-app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-DEUREKA_URI=http://eureka-server:8761","-DBROKER_URI=http://broker:29092","-jar","/app.jar"]