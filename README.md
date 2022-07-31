<h1 align="center"> Financial Transaction Analyzer </h1>


## Sobre o desafio proposto no Challenge Backend

Aplicação Web tradicional(server-side) para realizar análise de milhares de transações financeiras e identificar possíveis transações suspeitas.

## Objetivos do projeto

O projeto foi desenvolvido em sprints com duração 1 semana cada, que possuiam determinadas atividades a serem implementadas. Para uma melhor gestão das atividades, foi utilizado o trello como ferramenta.

- [Trello da Sprint 1](https://trello.com/b/6BVMlCYd/challenge-backend-3-semana-1)
- [Trello da Sprint 2](https://trello.com/b/nUN64cpL/challenge-backend-3-semana-2)
- [Trello da Sprint 3](https://trello.com/b/Z5fKD7ly/challenge-backend-3-semana-3)

## Tecnologias

 A linguagem de programação, frameworks e tecnologias eram de livre escolha. Eu escolhi desenvolver o projeto com as seguintes tecnologias:
 
<img alt="Java" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" width="50" height="50" /> <img alt="Spring" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original-wordmark.svg" width="50" height="50" /> <img alt="Mongo-DB" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mongodb/mongodb-plain-wordmark.svg" width="50" height="50" /> <img alt="Docker" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-plain-wordmark.svg" width="50" height="50" /> <img alt="Amazon Web Services" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/amazonwebservices/amazonwebservices-plain-wordmark.svg" width="50" height="50" /> <img alt="Prometheus" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/prometheus/prometheus-original-wordmark.svg" width="50" height="50" /> <img alt="Grafana" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/grafana/grafana-original-wordmark.svg" width="50" height="50" />

## Deploy

O deploy da aplicação foi realizado em uma instância do EC2 da AWS, podendo ser acessada por esse [link](http://ec2-15-228-229-105.sa-east-1.compute.amazonaws.com/)
(Versão branch main)

Para rodar localmente é necessário ter instalado o Docker compose.

- Abra o terminal e clone o projeto usando o comando
"git clone https://github.com/WesleyMime/financial-transaction-analyzer.git"

- Entre na pasta "financial-transaction-analyzer" e use o comando "docker-compose up".

Se tudo estiver funcionando, os links para acessar são: 
- Aplicação http://localhost/
- Grafana http://localhost:3000/
