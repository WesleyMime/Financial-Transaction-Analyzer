<h1 align="center"> Financial Transaction Analyzer </h1>

  | :placard: Vitrine.Dev |     |
  | -------------  | --- |
  | :sparkles: Nome        | **Financial Transaction Analyzer**
  | :label: Tecnologias | Java, Spring, Mongo-DB, Docker, Google Cloud, Prometheus, Grafana, Kafka
  | :rocket: URL         | http://34.176.103.0
  | :fire: Desafio     | https://www.alura.com.br/challenges/back-end-3/

![](https://user-images.githubusercontent.com/55067868/191625101-32435bfc-0814-4246-8805-a4273e6ce027.png#vitrinedev)

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/WesleyMime/Financial-Transaction-Analyzer/blob/microservices/README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-g.svg)](https://github.com/WesleyMime/Financial-Transaction-Analyzer/blob/microservices/README.pt-br.md)

## Sobre o desafio proposto no Challenge Backend

Aplicação Web tradicional(server-side) para realizar análise de milhares de transações financeiras e identificar possíveis transações suspeitas.

## Objetivos do projeto

O projeto foi desenvolvido em sprints com duração 1 semana cada, que possuiam determinadas atividades a serem implementadas. Para uma melhor gestão das atividades, foi utilizado o trello como ferramenta.

- [Trello da Sprint 1](https://trello.com/b/6BVMlCYd/challenge-backend-3-semana-1)
- [Trello da Sprint 2](https://trello.com/b/nUN64cpL/challenge-backend-3-semana-2)
- [Trello da Sprint 3](https://trello.com/b/Z5fKD7ly/challenge-backend-3-semana-3)

## Tecnologias

 A linguagem de programação, frameworks e tecnologias eram de livre escolha. Eu escolhi desenvolver o projeto com as seguintes tecnologias:
 
<img alt="Java" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" width="50" height="50" /> <img alt="Spring" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original-wordmark.svg" width="50" height="50" /> <img alt="Mongo-DB" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mongodb/mongodb-plain-wordmark.svg" width="50" height="50" /> <img alt="Docker" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-plain-wordmark.svg" width="50" height="50" /> <img alt="Google Cloud" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/googlecloud/googlecloud-original.svg" width="50" height="50" /> <img alt="Prometheus" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/prometheus/prometheus-original-wordmark.svg" width="50" height="50" /> <img alt="Grafana" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/grafana/grafana-original-wordmark.svg" width="50" height="50" /> <img alt="Kafka" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/apachekafka/apachekafka-original-wordmark.svg" width="50" height="50" />

## Deploy


O deploy da aplicação foi realizado em uma instância da Google Cloud Compute Engine, podendo ser acessada por esse [link](http://34.176.103.0/)

Para rodar localmente é necessário ter Docker instalado.

- Abra o terminal e clone o projeto usando o comando
"git clone https://github.com/WesleyMime/financial-transaction-analyzer.git"

- Entre na pasta "financial-transaction-analyzer" e use o comando "docker-compose up".

Se tudo estiver funcionando, os links para acessar são: 
- Aplicação http://localhost/
- Grafana http://localhost:3000/
- Eureka http://localhost:8761
- Spring Boot Admin http://localhost:8761/admin

