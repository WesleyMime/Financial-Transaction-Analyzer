FROM openjdk:25-slim-bullseye
VOLUME /main-app
RUN addgroup --system spring && adduser --system spring && adduser spring spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-DEUREKA_URI=http://eureka-server:8761","-DBROKER_URI=http://broker:29092","-jar","/app.jar"]