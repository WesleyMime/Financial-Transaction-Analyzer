<div align="center">
<h1> Financial Transaction Analyzer </h1>

[![en][en-shield]][en-url]
[![pt-br][pt-br-shield]][pt-br-url]
[![project_license][license-shield]][license-url]
[![last-commit][commit-shield]][commit-url]
![workflow][workflow-shield]
![deploy-status]

</div>

![](https://user-images.githubusercontent.com/55067868/191625101-32435bfc-0814-4246-8805-a4273e6ce027.png#vitrinedev)

## Description

Financial Transaction Analyzer is a web application designed to analyze financial transactions using a microservices
architecture. It leverages modern technologies like Java, Spring Boot, MongoDB, Kafka, Docker, and Kubernetes to provide
a scalable and efficient solution.

## Project objectives

The project was developed in sprints lasting 1 week each, which had certain activities to be implemented. For better management of activities, trello was used.

- [Sprint 1 Trello](https://trello.com/b/6BVMlCYd/challenge-backend-3-semana-1)
- [Sprint 2 Trello](https://trello.com/b/nUN64cpL/challenge-backend-3-semana-2)
- [Sprint 3 Trello](https://trello.com/b/Z5fKD7ly/challenge-backend-3-semana-3)

## Key Features

- Built with **Java** and **Spring Boot** for robust backend services.
- Uses **MongoDB** for flexible and scalable data storage.
- Implements **Kafka** for event-driven communication between microservices.
- Fully containerized with **Docker** for seamless deployment.
- Orchestrated using **Kubernetes** for high availability and scalability.
- Microservices architecture for modularity and scalability.

## Technologies

![java] ![spring] ![mongodb] ![docker] ![kubernetes] ![kafka] ![prometheus] ![grafana]

## Deploy

The Web Application is running on an Oracle Cloud Infrastructure Kubernetes Cluster, with DNS and HTTPS from Cloudflare.

The links to access are:

- Web Application https://fta.marujo.site
- Eureka https://fta-ops.marujo.site
- Spring Boot Admin https://fta-ops.marujo.site/admin

![kubernetes-grafana](https://github.com/user-attachments/assets/eaac0c8b-87f6-483b-b85a-f1a8dacf7133)

## Run locally


To run locally you need to have Docker installed.

- Clone project

```
git clone https://github.com/WesleyMime/financial-transaction-analyzer.git
```

- Enter the project folder

```
cd financial-transaction-analyzer
```

- Start services

```
docker compose up
```

### Usage

Once the application is running, you can use it to analyze financial transactions. The application provides a web
interface to process and visualize transaction data.

The links to access are:

- Web application http://localhost
- Grafana http://localhost:3000
- Eureka http://localhost:8761
- Spring Boot Admin http://localhost:8761/admin

## License

Distributed under the MIT license. See `LICENSE.txt` for more information.

[en-shield]: https://img.shields.io/badge/lang-en-green.svg?style=for-the-badge
[en-url]: https://github.com/WesleyMime/Financial-Transaction-Analyzer/blob/microservices/README.md
[pt-br-shield]: https://img.shields.io/badge/lang-pt--br-lightdarkgreen.svg?style=for-the-badge
[pt-br-url]: https://github.com/WesleyMime/Financial-Transaction-Analyzer/blob/microservices/README.pt-br.md
[commit-shield]: https://img.shields.io/github/last-commit/wesleymime/Financial-Transaction-Analyzer.svg?style=for-the-badge
[commit-url]: https://github.com/wesleymime/Financial-Transaction-Analyzer/commit
[license-shield]: https://img.shields.io/github/license/wesleymime/Financial-Transaction-Analyzer.svg?style=for-the-badge
[license-url]: https://github.com/wesleymime/Financial-Transaction-Analyzer/blob/master/LICENSE.txt
[workflow-shield]: https://img.shields.io/github/actions/workflow/status/wesleymime/Financial-Transaction-Analyzer/.github/workflows/main.yml?style=for-the-badge
[workflow-url]: https://img.shields.io/github/actions/workflow/status/wesleymime/Financial-Transaction-Analyzer/.github/workflows/main.yml

[deploy-status]: http://167.234.233.130:3001/api/badge/4/status?upColor=lightdarkgreen&style=for-the-badge

[java]: https://img.shields.io/badge/Java-000000?logo=openjdk&logoColor=white&style=for-the-badge
[spring]: https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff&style=for-the-badge
[mongodb]: https://img.shields.io/badge/MongoDB-47A248.svg?logo=mongodb&logoColor=white&style=for-the-badge
[docker]: https://img.shields.io/badge/docker-2496ED?logo=docker&logoColor=white&style=for-the-badge
[kubernetes]: https://img.shields.io/badge/kubernetes-326CE5?logo=kubernetes&logoColor=white&style=for-the-badge
[kafka]: https://img.shields.io/badge/apache%20kafka-231F20?style=for-the-badge
[prometheus]: https://img.shields.io/badge/prometheus-E6522C?&logo=prometheus&logoColor=white&style=for-the-badge
[grafana]: https://img.shields.io/badge/grafana-F46800?logo=grafana&logoColor=white&style=for-the-badge