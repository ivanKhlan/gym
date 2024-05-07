# Gym Application

* [General info](#general-info)
* [Api-docs](#api-docs)
* [Technologies](#technologies)
* [Set-up](#set-up)

## General Info

- The applicationEntity has two configurations (profiles), dev and prod. Dev is for local development, and prod is for production, so use profile dev when developing locally, logging is also configured for two profiles, for dev logs are written
  to the console (Level DEBUG, if necessary, in the log4j2-dev.xml file, you can change Root level="debug" to Root level="info", to output fewer logs), and on production to a file (Level INFO).
- A dependency has been added to support docker-compose, so if you have any problems with docker, you can comment out the dependency.
- So, good luck :)

## Api-docs

> - Swagger UI: http://localhost:8080/swagger-ui/index.html

## Technologies
- Spring Boot 3.2.2
- Spring Data JPA (Hibernate)
- Spring Actuator
- Spring Devtools
- Spring Security
- Spring Test (JUnit 5, AssertJ, Mockito)
- Hibernate Validator
- Java 17
- Logger (log4j2)
- Lombok
- PostgreSQL
- Flyway
- Swagger (v.3)
- Docker
- Maven

## Set-up
`cd existing_repo` <br>
`git remote add origin https://gitlab.com/gvamburg/gym_backend_spring.git` <br>
`git branch -M main` <br>
`git push -uf origin main`
