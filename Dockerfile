FROM maven:latest
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

COPY pom.xml $HOME/pom.xml
COPY common $HOME/common
COPY email-service $HOME/email-service
COPY eureka-server $HOME/eureka-server
COPY fraud-service $HOME/fraud-service
COPY fta $HOME/fta
COPY transactions-generator-service $HOME/transactions-generator-service
RUN mvn clean package -Dmaven.test.skip