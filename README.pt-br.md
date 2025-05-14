<div align="center">
<h1> Financial Transaction Analyzer </h1>

[![en][en-shield]][en-url]
[![pt-br][pt-br-shield]][pt-br-url]
[![project_license][license-shield]][license-url]
[![last-commit][commit-shield]][commit-url]
![workflow][workflow-shield]
[![deploy][deploy-status]][deploy-url]

</div>

![](https://user-images.githubusercontent.com/55067868/191625101-32435bfc-0814-4246-8805-a4273e6ce027.png#vitrinedev)

## Descrição

Financial Transaction Analyzer é uma aplicação web projetada para analisar transações financeiras usando arquitetura de
microsserviços.
Ela aproveita tecnologias modernas como Java, Spring Boot, MongoDB, Kafka, Docker e Kubernetes para fornecer uma solução
escalável e eficiente.

## Objetivos do projeto

O projeto foi desenvolvido em sprints com duração 1 semana cada, que possuiam determinadas atividades a serem
implementadas.
Para uma melhor gestão das atividades, foi utilizado o trello como ferramenta.

- [Trello da Sprint 1](https://trello.com/b/6BVMlCYd/challenge-backend-3-semana-1)
- [Trello da Sprint 2](https://trello.com/b/nUN64cpL/challenge-backend-3-semana-2)
- [Trello da Sprint 3](https://trello.com/b/Z5fKD7ly/challenge-backend-3-semana-3)

## Características principais

- Construído com **Java** e **Spring Boot** Para serviços de back-end robustos.
- Usa **MongoDB** para armazenamento de dados flexível e escalável.
- Implementa **Kafka** para comunicação orientada a eventos entre microsserviços.
- Totalmente contêinerzado com **Docker** para deploy sem problemas.
- Orquestrado usando **Kubernetes** para alta disponibilidade e escalabilidade.
- Arquitetura de microsserviços para modularidade e escalabilidade.

## Tecnologias

![java] ![spring] ![mongodb] ![docker] ![kubernetes] ![kafka] ![prometheus] ![grafana] ![oracle] ![cloudflare]

## Deploy

A Aplicação web está rodando em um Cluster Kubernetes da Oracle Cloud Infrastructure, com DNS e HTTPS da Cloudflare.

Os links para o acesso são:

- Aplicação Web https://fta.marujo.site
- Eureka https://fta-ops.marujo.site
- Spring Boot Admin https://fta-ops.marujo.site/admin

![kubernetes-grafana](https://github.com/user-attachments/assets/eaac0c8b-87f6-483b-b85a-f1a8dacf7133)

## Rode localmente

Para rodar é necessário ter Docker instalado.

- Clone o projeto

```
git clone https://github.com/WesleyMime/financial-transaction-analyzer.git
```

- Entre na pasta do projeto

```
cd financial-transaction-analyzer
```

- Inicie os serviços

```
docker compose up
```

### Uso

Depois que o aplicativo estiver em execução, você pode usá-lo para analisar transações financeiras. O aplicativo
fornece uma interface web para processar e visualizar os dados de transações.

Os links para o acesso são:

- Aplicação Web http://localhost/
- Grafana http://localhost:3000/
- Eureka http://localhost:8761
- Spring Boot Admin http://localhost:8761/admin

## Licença

Distribuído sob a licença do MIT. Consulte LICENSE.txt para obter mais informações.

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

[deploy-url]: https://fta.marujo.site

[java]: https://img.shields.io/badge/Java-000000?logo=openjdk&logoColor=white&style=for-the-badge
[spring]: https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff&style=for-the-badge
[mongodb]: https://img.shields.io/badge/MongoDB-47A248.svg?logo=mongodb&logoColor=white&style=for-the-badge
[docker]: https://img.shields.io/badge/docker-2496ED?logo=docker&logoColor=white&style=for-the-badge
[kubernetes]: https://img.shields.io/badge/kubernetes-326CE5?logo=kubernetes&logoColor=white&style=for-the-badge
[kafka]: https://img.shields.io/badge/apache%20kafka-231F20?style=for-the-badge
[prometheus]: https://img.shields.io/badge/prometheus-E6522C?&logo=prometheus&logoColor=white&style=for-the-badge
[grafana]: https://img.shields.io/badge/grafana-F46800?logo=grafana&logoColor=white&style=for-the-badge

[oracle]: https://custom-icon-badges.demolab.com/badge/Oracle%20Cloud-F80000?logo=oracle&logoColor=white&style=for-the-badge

[cloudflare]: https://img.shields.io/badge/Cloudflare-F38020?logo=Cloudflare&logoColor=white&style=for-the-badge