<h1 align="center"> Financial Transaction Analyzer </h1>

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/WesleyMime/Financial-Transaction-Analyzer/blob/microservices/README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-g.svg)](https://github.com/WesleyMime/Financial-Transaction-Analyzer/blob/microservices/README.pt-br.md)

## About the Challenge Backend

Traditional web application (server-side) to perform analysis of thousands of financial transactions and identify possible suspicious transactions.

## Project objectives

The project was developed in sprints lasting 1 week each, which had certain activities to be implemented. For better management of activities, trello was used.

- [Sprint 1 Trello](https://trello.com/b/6BVMlCYd/challenge-backend-3-semana-1)
- [Sprint 2 Trello](https://trello.com/b/nUN64cpL/challenge-backend-3-semana-2)
- [Sprint 3 Trello](https://trello.com/b/Z5fKD7ly/challenge-backend-3-semana-3)

## Technologies

The programming language, frameworks and technologies were of free choice. I chose to develop the project with the following technologies:

<img alt="Java" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" width="50" height="50" /> <img alt="Spring" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original-wordmark.svg" width="50" height="50" /> <img alt="Mongo-DB" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mongodb/mongodb-plain-wordmark.svg" width="50" height="50" /> <img alt="Docker" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-plain-wordmark.svg" width="50" height="50" /> <img alt="Google Cloud" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/googlecloud/googlecloud-original.svg" width="50" height="50" /> <img alt="Prometheus" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/prometheus/prometheus-original-wordmark.svg" width="50" height="50" /> <img alt="Grafana" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/grafana/grafana-original-wordmark.svg" width="50" height="50" /> <img alt="Kafka" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/apachekafka/apachekafka-original-wordmark.svg" width="50" height="50" />

## Deploy


The application was deployed on a Google Cloud Compute Engine instance, which can be accessed through this [link](http://34.176.200.24/)

To run locally you need to have Docker installed.

- Open the terminal and clone the project using the command
"git clone https://github.com/WesleyMime/financial-transaction-analyzer.git"

- Enter the "financial-transaction-analyzer" folder and use the command "docker-compose up".

If everything is working, the links to access are:
- Web application http://localhost/
- Grafana http://localhost:3000/
- Eureka http://localhost:8761
- Spring Boot Admin http://localhost:8761/admin

